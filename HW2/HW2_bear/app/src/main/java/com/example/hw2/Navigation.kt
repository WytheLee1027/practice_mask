package com.example.hw2

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

data class Landmark(val id: Int, val name: String, val image: Int, val description: String)

val landmarks = listOf(
    Landmark(1, "東清秘境", R.drawable.img1, "蘭嶼東清部落與情人洞之間的礁岩地帶有一座珊瑚礁岩洞「東清秘境」，過去是當地人戲水的秘境，現在隨著網路社群網路的發展，在IG上都能看見許多關於東清秘境的美照，成為許多人喜歡找尋秘境、或是愛拍攝美景的旅客來到蘭嶼旅遊時會慕名前往的地方，在網路上能找到的資料也不少。"),
    Landmark(2, "乳頭山", R.drawable.img2, "乳頭山是蘭嶼著名的步道景點，海拔約60公尺，規劃有木棧道，至最高處往返大約只需要30~40分鐘。山頂視野遼闊，能夠眺望東清灣、東清部落以及情人洞，是許多人推薦來到蘭嶼一定要爬上來看看的絕景景點。"),
    Landmark(3, "青青草原", R.drawable.img3, "位於西南方的青青草原，是追逐日落絕佳的地點！沿著步道走，身邊的風景也漸漸明朗，放眼望去是延伸至懸崖邊的草原，山頭就在眼底延綿著，約十幾分鐘的路程就能走到離日落最近的地方。蘭嶼的日落，就彷彿在散播溫暖一般，讓每個人的臉色都染上一層漂亮的金黃。"),
    Landmark(4, "像水渠一樣", R.drawable.img4, "像水渠一樣兩側大岩壁框出一大片湛藍大海+天空，因為地勢的關係，騎車過來由高往低望去很像要奔進大海，陽光照射在水面上波光粼粼，加上時不時被風吹拂的白色海浪，完全被蘭嶼景色懾服，路過千萬別錯過這個可愛的小景點。"),
    Landmark(5, "舊蘭嶼燈塔", R.drawable.img5, "曾是太平洋航線的重要指引，後因照明效果不佳廢棄。雖然失去原有功能，塔上絕美的藍海夕陽仍促使大批旅客朝聖。從開元港慢慢走到燈塔附近，上山步道看似漫長，實際上非常容易解鎖。"),
    Landmark(6, "朗島秘境", R.drawable.img6, "小丑魚是這裡的主人，HD 高畫質海水是最好的陪襯。朗島秘境像一顆未經雕琢的翡翠，和東清秘境一樣被珊瑚礁岩環繞，卻具有更深層的魅力。不只可以浮潛賞魚，也可以輕鬆浮在水面上，安靜享受一整天。"),
    Landmark(7, "角落Bais咖啡", R.drawable.img7, "她告訴我，「角落」(Bais)是母親賦予她的名字，不只是因為她出生在蘭嶼朗島部落深處的角落，也有她「能夠到世界各個角落」的意思。所以「Bais」，不只是她的根，也是她的夢想。唸完高中，準備升大學的那一年，母親在山上種田準備下山時摔傷。在醫院時．她的母親說：「因為家裡正準備要蓋房子需要ㄧ筆錢，我們家境也不算富有，所以可以請你暫時先別升大學嗎？」於是Bais把自己存的錢，都給了家，只留了2000元在自己身上，就到台北投靠表姊，並在台北租了一間沒有窗、只夠放一張床的兩坪房，一住就是七年。那是她在台北最孤單的角落。那些年，Bais常常把工資都寄回家，自己只留500元在身上，過著用吐司打發三餐、在酒吧和咖啡廳打工的生活。讓她印象最深刻的，是19歲的生日，沒有母親做的芋頭糕、沒有大肆喧鬧的慶祝。她依然在酒吧上班，直到凌晨一點才準備搭公車回家，但那一晚，她卻在大雨中錯過了班車，身上只剩下6塊錢，求助無門的她，只好獨自在公車站待了一整夜，直到天亮，搭上第一班公車回家，匆匆洗個澡，一夜未眠的她，又出門打工了。結束了五年的酒吧生活，在朋友的介紹下，Bais開始在「朵兒咖啡館」工作，她發現自己很喜歡做蛋糕，並開始夢想有自己的咖啡店。同事們對她就像家人一樣，對她而言，「朵兒咖啡館」不只是電影的場景，更是她在台北的歸屬、和開始擁有未來「要開一間自己的咖啡店」的夢想角落。等家裡經濟狀況改善了，她告訴父母，她想存一筆錢，回蘭嶼蓋一間屬於自己的房子。原本只是想自住，希望有時間能夠陪伴逐漸年邁的父母，卻誤打誤撞的開起了自己的咖啡店，就叫做「角落Bais」。"),
    Landmark(8, "情人洞", R.drawable.img8, "太平洋風光從 5 丈高的海蝕洞展開，四處皆是潮水切出的銳利稜角。洞口下方嵌著閃亮深潭，附近積水映出情人洞倒影，隨意擺個 pose，就能無違和地融入這片背景之中。當一波波大浪猛烈拍擊岩壁，一邊聽濤、一邊觀潮，無法用任何文字描述的震撼，只有親自到場才能體驗。")
)

@Composable
fun Navigation() {
    val navigation = rememberNavController()
    NavHost(navController = navigation, startDestination = Screen.MainScreen.route) {
        composable(Screen.MainScreen.route) {
            MainScreen(navController = navigation)
        }
        composable(
            route = Screen.DetailScreen.route + "/{landmarkId}",
            arguments = listOf(
                navArgument("landmarkId") {
                    type = NavType.IntType
                    defaultValue = 1
                }
            )
        ) {
            DetailScreen(navController = navigation, landmarkId = it.arguments?.getInt("landmarkId"))
        }
    }
}

@Composable
fun MainScreen(navController: NavController) {
    Column {
        Text(
            text = "蘭嶼回憶錄",
            fontSize = 28.sp,
            color = Color(0xFF0D47A1),
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.CenterHorizontally)
        )
        LazyColumn {
            items(landmarks) { landmark ->
                LandmarkCard(landmark, navController)
            }
        }
    }
}

@Composable
fun LandmarkCard(landmark: Landmark, navController: NavController) {
    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .clickable {
                Log.v("TAG", "LandmarkCard: ${landmark.id}")
                navController.navigate(Screen.DetailScreen.withArgs(landmark.id.toString()))
            },
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFBBDEFB)
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Image(
                painter = painterResource(id = landmark.image),
                contentDescription = landmark.name,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .height(200.dp)
                    .fillMaxWidth()
            )
            Text(text = landmark.name, fontSize = 20.sp, color = Color(0xFF0D47A1))
            Text(
                text = landmark.description,
                fontSize = 14.sp,
                color = Color(0xFF0D47A1),
                maxLines = 4,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun DetailScreen(navController: NavController, landmarkId: Int?) {
    val landmark = landmarks.find { it.id == landmarkId }
    Log.v("TAG", "LandmarkCard in detail: ${landmark!!.id}")
    val context = LocalContext.current

    LazyColumn(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
            .background(Color(0xFFE3F2FD)),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_launcher_background),
                        contentDescription = stringResource(id = R.string.back),
                        tint = Color(0xFF0D47A1)
                    )
                }
                Text(text = landmark!!.name, fontSize = 24.sp, color = Color(0xFF0D47A1))
            }
        }
        item {
            Image(
                painter = painterResource(id = landmark!!.image),
                contentDescription = landmark.name,
                modifier = Modifier
                    .height(300.dp)
                    .fillMaxWidth()
                    .padding(8.dp)
            )
        }
        item {
            Text(
                text = landmark.name,
                fontSize = 24.sp,
                color = Color(0xFF0D47A1),
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        }
        item {
            Text(
                text = landmark.description,
                fontSize = 16.sp,
                color = Color(0xFF0D47A1),
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        }
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Button(
                    onClick = {
                        val gmmIntentUri = Uri.parse("geo:0,0?q=${Uri.encode(landmark.name)}")
                        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                        mapIntent.setPackage("com.google.android.apps.maps")
                        context.startActivity(mapIntent)
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0D47A1)),
                    modifier = Modifier.align(Alignment.Center)
                ) {
                    Text(text = "Show on Google Maps", color = Color.White)
                }
            }
        }
    }
}
