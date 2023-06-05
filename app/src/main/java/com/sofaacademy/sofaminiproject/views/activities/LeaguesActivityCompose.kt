package com.sofaacademy.sofaminiproject.views.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.sofaacademy.sofaminiproject.R
import com.sofaacademy.sofaminiproject.model.TabRowItem
import com.sofaacademy.sofaminiproject.utils.Constants
import com.sofaacademy.sofaminiproject.utils.pagerTabIndicatorOffset
import com.sofaacademy.sofaminiproject.views.activities.ui.theme.Blue
import com.sofaacademy.sofaminiproject.views.activities.ui.theme.SofaMiniProjectTheme
import kotlinx.coroutines.launch

class LeaguesActivityCompose : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SofaMiniProjectTheme(darkTheme = false) {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    Scaffold(topBar = {
                        Column {
                            MyTopAppBar()
                            TabLayout()
                        }
                    }) { it ->
                        Column(modifier = Modifier.padding(it)) {
                            TabLayout()
                        }
                    }


                    Greeting("Android")
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
        title = { Text(text = "Abcde", color = MaterialTheme.colorScheme.surface) },
        navigationIcon = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Go back",
                    tint = MaterialTheme.colorScheme.surface
                )
            }
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = Blue,
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
            screen = { TabScreen(slug = Constants.SLUG_FOOTBALL) },
            iconRes = R.drawable.icon_football
        ),
        TabRowItem(
            title = stringResource(id = R.string.title_basketball),
            screen = { TabScreen(slug = Constants.SLUG_BASKETBALL) },
            iconRes = R.drawable.icon_basketball
        ),
        TabRowItem(
            title = stringResource(id = R.string.title_american_football),
            screen = { TabScreen(slug = Constants.SLUG_AMERICAN_FOOTBALL) },
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
                    .padding(horizontal = 8.dp),
                color = MaterialTheme.colorScheme.surface
            )
        },
    ) {
        tabRowItems.forEachIndexed { index, item ->
            Tab(
                selected = currentPage == index,
                modifier = Modifier.background(Blue),
                onClick = {
                    coroutineScope.launch { pagerState.animateScrollToPage(index) }
                },
                icon = {
                    Icon(painter = painterResource(id = item.iconRes),
                        tint = MaterialTheme.colorScheme.surface,
                        contentDescription = "")
                },
                text = {
                    Text(
                        text = item.title,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = Color.White,
                        fontSize = 12.sp
                    )
                }
            )
        }
    }
    HorizontalPager(
        count = tabRowItems.size,
        state = pagerState,
    ) {
        tabRowItems[pagerState.currentPage].screen()
    }

}

@Composable
fun TabScreen(
    slug: String,
) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = slug,
        )
    }
}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SofaMiniProjectTheme {
        Greeting("Android")
    }
}