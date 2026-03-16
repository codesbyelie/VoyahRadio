package elie.voyah.radio.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import elie.voyah.radio.app.theme.Shapes
import elie.voyah.radio.app.theme.extraSmall
import elie.voyah.radio.app.theme.LocalFeedScale
import elie.voyah.radio.app.theme.medium
import elie.voyah.radio.app.theme.small
import elie.voyah.radio.domain.Radio
import elie.voyah.radio.presentation.home.components.ErrorMsgView
import elie.voyah.radio.presentation.home.components.Feed
import elie.voyah.radio.presentation.home.components.FeedTitle
import elie.voyah.radio.presentation.home.components.FullScreenDialog
import elie.voyah.radio.presentation.home.components.HomeTopAppBar
import elie.voyah.radio.presentation.home.components.RadioGridItem
import elie.voyah.radio.presentation.home.components.RadioHorizontalGridItem
import elie.voyah.radio.presentation.home.components.RadioSearchResult
import elie.voyah.radio.presentation.home.components.row
import elie.voyah.radio.presentation.home.components.single
import elie.voyah.radio.presentation.home.components.title
import voyahradio.composeapp.generated.resources.Res
import voyahradio.composeapp.generated.resources.about_app
import voyahradio.composeapp.generated.resources.app_name
import voyahradio.composeapp.generated.resources.contact_email
import voyahradio.composeapp.generated.resources.contact_label
import voyahradio.composeapp.generated.resources.developer
import voyahradio.composeapp.generated.resources.github_label
import voyahradio.composeapp.generated.resources.github_url
import voyahradio.composeapp.generated.resources.ic_launcher
import voyahradio.composeapp.generated.resources.last_updated_date
import voyahradio.composeapp.generated.resources.last_updated_label
import voyahradio.composeapp.generated.resources.license_label
import voyahradio.composeapp.generated.resources.license_type
import voyahradio.composeapp.generated.resources.maintainer
import voyahradio.composeapp.generated.resources.recently_updated
import voyahradio.composeapp.generated.resources.version_label
import voyahradio.composeapp.generated.resources.version_number
import voyahradio.composeapp.generated.resources.webpage_label
import voyahradio.composeapp.generated.resources.webpage_url

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    rootNavController: NavController,
    viewModel: HomeViewModel = koinViewModel()
) {
    val gridState = rememberLazyGridState()
    val uniqueRadios = viewModel.radios.distinctBy { it.id to it.url }
    val radiosSize by mutableStateOf(uniqueRadios.size)
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val bottomSheetState = rememberBottomSheetScaffoldState()
    val scope = rememberCoroutineScope()

    BottomSheetScaffold(
        modifier = Modifier.fillMaxSize().nestedScroll(scrollBehavior.nestedScrollConnection),
        scaffoldState = bottomSheetState,
        sheetContent = {
            viewModel.selectedRadio?.let {
                viewModel.playerRepository.PLayerUI(
                    radio = it,
                    isSaved = viewModel.isSaved,
                    onSaveClick = {
                        viewModel.saveRadio(it)
                    }
                )
            }
        },
        sheetPeekHeight = if (viewModel.selectedRadio != null) BottomSheetDefaults.SheetPeekHeight else 0.dp,
        topBar = {
            HomeTopAppBar(
                rootNavController = rootNavController,
                isSearchActive = viewModel.isSearchActive,
                onAboutClick = viewModel::toggleAboutDialog,
                toggleSearch = viewModel::toggleSearch,
                searchQuery = viewModel.searchQuery,
                updateSearchQuery = viewModel::updateSearchQuery,
                scrollBehavior = scrollBehavior,
                searchResultContent = {
                    RadioSearchResult(
                        isSearchLoading = viewModel.isSearchLoading,
                        searchErrorMsg = viewModel.searchErrorMsg,
                        searchResult = viewModel.searchResult,
                        onRadioClick = {
                            viewModel.toggleSearch()
                            scope.launch { delay(1000) }
                            viewModel.selectedRadio(it)
                            scope.launch { bottomSheetState.bottomSheetState.expand() }
                            viewModel.play(it.url)
                        }
                    )
                }
            )
        }
    ) { contentPadding ->
        val fraction = viewModel.stationCellSizeFraction
        val minCellWidthDp = (120 + fraction * 80).toInt().coerceIn(100, 220).dp
        val feedScale = (0.85f + fraction * 0.35f).coerceIn(0.85f, 1.2f)
        CompositionLocalProvider(LocalFeedScale provides feedScale) {
        Feed(
            modifier = Modifier.fillMaxSize(),
            columns = GridCells.Adaptive(minCellWidthDp),
            state = gridState,
            contentPadding = contentPadding,
            verticalArrangement = Arrangement.spacedBy(0.dp),
            horizontalArrangement = Arrangement.spacedBy(0.dp)
        ) {

            if (viewModel.savedRadios != emptyList<Radio>()) {
                title(contentType = "Saved-title") {
                    FeedTitle(
                        title = "Saved Radios",
                        onClick = { }
                    )
                }

                row(contentType = "verified-shimmer-effect") {
                    RadioHorizontalGridItem(
                        radios = viewModel.savedRadios,
                        onRadioClick = {
                            viewModel.selectedRadio(it)
                            scope.launch { bottomSheetState.bottomSheetState.expand() }
                            viewModel.play(it.url)
                        }
                    )
                }
            }

            title(contentType = "latest-title") { FeedTitle(title = stringResource(Res.string.recently_updated)) }

            items(
                count = radiosSize,
                key = { uniqueRadios[it].id }
            ) { index ->
                val radio = uniqueRadios[index]
                if (index == radiosSize - 1 && !viewModel.isLoading && viewModel.errorMsg == null) {
                    viewModel.getRadios()
                }
                RadioGridItem(
                    radio = radio,
                    onClick = {
                        viewModel.selectedRadio(radio)
                        scope.launch { bottomSheetState.bottomSheetState.expand() }
                        viewModel.play(radio.url)
                    }
                )
            }

            if (viewModel.errorMsg != null) {
                single {
                    ErrorMsgView(
                        errorMsg = viewModel.errorMsg!!,
                        onRetryClick = {
                            viewModel.getRadios()
                        }
                    )
                }
            }
            if (viewModel.isLoading) {
                single {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center,
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.padding(extraSmall)
                        )
                    }
                }
            }
        }
        }
    }

    if (viewModel.isAboutDialogShowing) {
        ShowAboutDialog(
            onDismiss = { viewModel.toggleAboutDialog() }
        )
    }

}

@Composable
private fun ShowAboutDialog(
    onDismiss: () -> Unit,
) {
    FullScreenDialog(
        onDismissRequest = { onDismiss() },
        modifier = Modifier.padding(16.dp).clip(Shapes.medium),
        actions = {
            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(onClick = { onDismiss() }) {
                    Text("Close")
                }
            }
        },
        content = {
            Column(
                modifier = Modifier.verticalScroll(rememberScrollState())
            ) {
                Image(
                    modifier = Modifier.size(100.dp).padding(bottom = medium),
                    painter = painterResource(Res.drawable.ic_launcher),
                    contentDescription = "App Icon"
                )
                Text(
                    style = MaterialTheme.typography.titleLarge,
                    text = stringResource(Res.string.app_name)
                )
                Text(
                    style = MaterialTheme.typography.bodyMedium,
                    text = stringResource(Res.string.developer)
                )
                Text(
                    modifier = Modifier.padding(bottom = small),
                    style = MaterialTheme.typography.bodyMedium,
                    text = stringResource(Res.string.maintainer)
                )
                Spacer(
                    modifier = Modifier.height(2.dp).fillMaxWidth(0.8f)
                        .background(MaterialTheme.colorScheme.primary)
                )
                Text(
                    modifier = Modifier.padding(vertical = medium),
                    style = MaterialTheme.typography.bodyMedium,
                    text = stringResource(Res.string.about_app)
                )

                Text(
                    style = MaterialTheme.typography.labelLarge,
                    text = stringResource(Res.string.last_updated_label)
                )
                Text(
                    style = MaterialTheme.typography.bodyMedium,
                    text = stringResource(Res.string.last_updated_date)
                )

                Text(
                    modifier = Modifier.padding(top = medium),
                    style = MaterialTheme.typography.labelLarge,
                    text = stringResource(Res.string.contact_label)
                )
                Text(
                    style = MaterialTheme.typography.bodyMedium,
                    text = stringResource(Res.string.contact_email)
                )

                Text(
                    modifier = Modifier.padding(top = medium),
                    style = MaterialTheme.typography.labelLarge,
                    text = stringResource(Res.string.webpage_label)
                )
                Text(
                    style = MaterialTheme.typography.bodyMedium,
                    text = stringResource(Res.string.webpage_url)
                )

                Text(
                    modifier = Modifier.padding(top = medium),
                    style = MaterialTheme.typography.labelLarge,
                    text = stringResource(Res.string.github_label)
                )
                Text(
                    style = MaterialTheme.typography.bodyMedium,
                    text = stringResource(Res.string.github_url)
                )

                Text(
                    modifier = Modifier.padding(top = medium),
                    style = MaterialTheme.typography.labelLarge,
                    text = stringResource(Res.string.license_label)
                )
                Text(
                    style = MaterialTheme.typography.bodyMedium,
                    text = stringResource(Res.string.license_type)
                )

                Text(
                    modifier = Modifier.padding(top = medium),
                    style = MaterialTheme.typography.labelLarge,
                    text = stringResource(Res.string.version_label)
                )
                Text(
                    style = MaterialTheme.typography.bodyMedium,
                    text = stringResource(Res.string.version_number)
                )
            }
        }
    )
}