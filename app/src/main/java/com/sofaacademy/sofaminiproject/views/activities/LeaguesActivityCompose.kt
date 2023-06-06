package com.sofaacademy.sofaminiproject.views.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.sofaacademy.sofaminiproject.R
import com.sofaacademy.sofaminiproject.model.TabRowItem
import com.sofaacademy.sofaminiproject.model.Tournament
import com.sofaacademy.sofaminiproject.utils.Constants
import com.sofaacademy.sofaminiproject.utils.pagerTabIndicatorOffset
import com.sofaacademy.sofaminiproject.viewmodel.TournamentsViewModel
import com.sofaacademy.sofaminiproject.views.activities.ui.theme.Blue
import com.sofaacademy.sofaminiproject.views.activities.ui.theme.SofaMiniProjectTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LeaguesActivityCompose : AppCompatActivity() {

    companion object {
        fun start(slugIndex: Int, context: Context) {
            val intent = Intent(context, LeaguesActivityCompose::class.java).apply {
                putExtra(Constants.SLUG_ARG, slugIndex)
            }
            ContextCompat.startActivity(context, intent, bundleOf())
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mode = AppCompatDelegate.getDefaultNightMode()

        setContent {
            SofaMiniProjectTheme(mode == MODE_NIGHT_YES) {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Scaffold(topBar = {
                        MyTopAppBar()
                    }) {
                        Column(modifier = Modifier.padding(it)) {
                            TabLayout()
                        }
                    }
                }
            }
        }
    }
}

@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopAppBar() {
    TopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.leagues_title),
                color = MaterialTheme.colorScheme.surface,
                fontWeight = FontWeight.Bold
            )
        },
        navigationIcon = {
            val activity = LocalContext.current as? Activity

            IconButton(onClick = {
                activity?.finish()
            }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Go back",
                    tint = MaterialTheme.colorScheme.surface
                )
            }
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary
        )
    )
}

@OptIn(ExperimentalPagerApi::class)
@Preview
@Composable
fun TabLayout(tournamentsViewModel: TournamentsViewModel = viewModel()) {
    val tabRowItems = listOf(
        TabRowItem(
            title = stringResource(id = R.string.title_football),
            slug = Constants.SLUG_FOOTBALL,
            iconRes = R.drawable.icon_football
        ),
        TabRowItem(
            title = stringResource(id = R.string.title_basketball),
            slug = Constants.SLUG_BASKETBALL,
            iconRes = R.drawable.icon_basketball

        ),
        TabRowItem(
            title = stringResource(id = R.string.title_american_football),
            slug = Constants.SLUG_AMERICAN_FOOTBALL,
            iconRes = R.drawable.icon_american_football
        )
    )

    val currentPage by remember {
        mutableStateOf(0)
    }
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()

    TabRow(
        selectedTabIndex = currentPage,
        modifier = Modifier.background(Blue),
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                Modifier
                    .pagerTabIndicatorOffset(
                        pagerState = pagerState,
                        tabPositions = tabPositions
                    )
                    .padding(horizontal = 8.dp)
                    .height(4.dp),
                color = MaterialTheme.colorScheme.surface
            )
        }
    ) {
        tabRowItems.forEachIndexed { index, item ->
            Tab(
                selected = currentPage == index,
                modifier = Modifier.background(MaterialTheme.colorScheme.primary),
                onClick = {
                    coroutineScope.launch { pagerState.animateScrollToPage(index) }
                },
                icon = {
                    Icon(
                        painter = painterResource(id = item.iconRes),
                        tint = MaterialTheme.colorScheme.surface,
                        contentDescription = ""
                    )
                },
                text = {
                    Text(
                        text = item.title,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = MaterialTheme.colorScheme.surface,
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp
                    )
                }
            )
        }
    }

    HorizontalPager(
        count = tabRowItems.size,
        state = pagerState
    ) {
        val item = tabRowItems[it]
        val tournamentsState by tournamentsViewModel.getTournamentsForSlug(item.slug)
            .observeAsState(initial = emptyList())
        if (tournamentsState.isEmpty()) {
            LeagueEmptyScreen()
        } else {
            TournamentItemsScreen(tournamentsState)
        }
    }
}

@Composable
fun LeagueEmptyScreen() {
    Box(modifier = Modifier.fillMaxSize())
}

@Composable
fun TournamentItemsScreen(
    tournaments: List<Tournament>
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        val context = LocalContext.current
        LazyColumn {
            items(tournaments) { tournament ->
                TournamentItem(tournament) { clickedTournament ->
                    TournamentDetailsActivity.start(clickedTournament, context)
                }
            }
        }
    }
}

@Composable
fun TournamentItem(
    tournament: Tournament,
    onClick: (tournament: Tournament) -> Unit
) {
    Row(
        modifier = Modifier
            .clickable {
                onClick(tournament)
            }
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Image(
            modifier = Modifier
                .padding(start = 16.dp)
                .height(40.dp)
                .width(40.dp),
            painter = rememberAsyncImagePainter(
                model = ImageRequest.Builder(LocalContext.current)
                    .data("${Constants.BASE_TOURNAMENT_URL}${tournament.id}${Constants.IMG_ENDPOINT}")
                    .build()
            ),
            contentDescription = stringResource(id = R.string.tournaments)
        )

        Text(
            modifier = Modifier
                .align(CenterVertically)
                .padding(horizontal = 16.dp),
            style = MaterialTheme.typography.headlineMedium,
            text = tournament.name,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

fun onTournamentClicked(tournament: Tournament, context: Context) =
    TournamentDetailsActivity.start(tournament, context)
