package com.sofaacademy.sofaminiproject.views.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
class LeaguesActivityCompose : ComponentActivity() {

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

        setContent {
            SofaMiniProjectTheme(darkTheme = false) {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Scaffold(topBar = {
                        Column {
                            MyTopAppBar()
                        }
                    }) { it ->
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
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        },
        navigationIcon = {
            IconButton(onClick = { }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Go back",
                    tint = Color.White
                )
            }
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = Blue
        )
    )
}

@OptIn(ExperimentalPagerApi::class)
@Preview
@Composable
fun TabLayout() {
    val tabRowItems = listOf(
        TabRowItem(
            title = stringResource(id = R.string.title_football),
            screen = { LeagueScreen(slug = Constants.SLUG_FOOTBALL) },
            iconRes = R.drawable.icon_football
        ),
        TabRowItem(
            title = stringResource(id = R.string.title_basketball),
            screen = { LeagueScreen(slug = Constants.SLUG_BASKETBALL) },
            iconRes = R.drawable.icon_basketball
        ),
        TabRowItem(
            title = stringResource(id = R.string.title_american_football),
            screen = { LeagueScreen(slug = Constants.SLUG_AMERICAN_FOOTBALL) },
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
                color = Color.White
            )
        }
    ) {
        tabRowItems.forEachIndexed { index, item ->
            Tab(
                selected = currentPage == index,
                modifier = Modifier.background(Blue),
                onClick = {
                    coroutineScope.launch { pagerState.animateScrollToPage(index) }
                },
                icon = {
                    Icon(
                        painter = painterResource(id = item.iconRes),
                        tint = Color.White,
                        contentDescription = ""
                    )
                },
                text = {
                    Text(
                        text = item.title,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = Color.White,
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
        tabRowItems[pagerState.currentPage].screen()
    }
}

@Composable
fun LeagueScreen(
    slug: String,
    tournamentsViewModel: TournamentsViewModel = viewModel()
) {
  /*  val leagues = tournamentsViewModel.tournamentsList.observeAsState(emptyList())
    tournamentsViewModel.getTournaments(slug)

    if (leagues.value.isEmpty()) {*/
        LeagueEmptyScreen()
   /* } else {
        LeagueItemsScreen(leagues.value)
    }*/
}

@Composable
fun LeagueEmptyScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "No items"
        )
    }
}

@Composable
fun LeagueItemsScreen(tournaments: List<Tournament>) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        LazyColumn {
            items(tournaments) { tournament ->
                TournamentItem(tournament)
            }
        }
    }
}

@Composable
fun TournamentItem(tournament: Tournament) {
    Row(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        /*Image(
            modifier = Modifier
                .height(40.dp)
                .width(40.dp),
            painter = rememberAsyncImagePainter(
                model = ImageRequest.Builder(LocalContext.current)
                    .data("${Constants.BASE_TOURNAMENT_URL}${tournament.id}${Constants.IMG_ENDPOINT}")
                    .build()
            ),
            contentDescription = stringResource(id = R.string.tournaments)
        )*/

        Text(
            modifier = Modifier
                .align(CenterVertically)
                .padding(horizontal = 16.dp),
            text = tournament.name,
            color = Color.Black
        )
    }
}

@Preview
@Composable
fun TournamentItemPreview() {
    Row(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Image(
            modifier = Modifier
                .height(40.dp)
                .width(40.dp),
            painter = painterResource(id = R.drawable.icon_football),
            contentDescription = stringResource(id = R.string.tournaments)
        )

        Text(
            modifier = Modifier
                .align(CenterVertically)
                .padding(horizontal = 16.dp),
            text = "Tournament",
            color = Color.Black
        )
    }
}
