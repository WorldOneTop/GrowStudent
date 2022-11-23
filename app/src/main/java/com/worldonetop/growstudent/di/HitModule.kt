package com.worldonetop.growstudent.di

import android.content.Context
import com.worldonetop.growstudent.lib.HitController
import com.worldonetop.growstudent.model.HitBox
import com.worldonetop.growstudent.source.Local
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HitModule {

    @Singleton
    @Provides
    fun providerHitCtl(@ApplicationContext context: Context):HitController{
        val d = context.resources.displayMetrics.density

        val hitCtl = HitController(arrayOf(
            // 구역
            HitBox(1241f*d, 188f*d, 1684f*d, 290f*d, arrayOf(10,13,16)),
            HitBox(1291f*d, 291f*d, 1685f*d, 331*d, arrayOf(10,13,16)),
            HitBox(1291f*d, 331f*d, 1600f*d, 365f*d, arrayOf(10,13,16)),
            HitBox(1333f*d, 370f*d, 1538f*d, 444f*d, arrayOf(10,13,16)),
            HitBox(1440f*d, 443f*d, 1539f*d, 496f*d, arrayOf(10,13,16)),
            HitBox(1540f*d, 475f*d, 1572f*d, 504f*d, arrayOf(10,13,16)),
            HitBox(1539f*d, 502f*d, 1600f*d, 533f*d, arrayOf(10,13,16)),
            HitBox( 1571f*d, 533f*d, 1628f*d, 600f*d, arrayOf(10,13,16)),
            HitBox(1734f*d, 358f*d, 1831f*d, 432f*d, arrayOf(19)),
            HitBox(908f*d, 332f*d, 1178f*d, 517f*d, arrayOf(33)),
            HitBox(1320f*d, 533f*d, 1564f*d, 576f*d, arrayOf(22)),
            HitBox(1236f*d, 576f*d, 1565f*d, 720f*d, arrayOf(22)),
            HitBox(1677f*d, 642f*d, 1764f*d, 740f*d, arrayOf(32)),
            HitBox(1950f*d, 479f*d, 2200f*d, 685f*d, arrayOf(34)),
            HitBox(2370f*d, 605f*d, 2511f*d, 707f*d, arrayOf(20, 35)),
            HitBox(2520f*d, 650f*d, 2750f*d, 1035f*d, arrayOf(24,25,26,27,28,29,30,31)),
            HitBox(2420f*d, 1036f*d, 2850f*d, 1360f*d, arrayOf(24,25,26,27,28,29,30,31)),
            HitBox(2392f*d, 850f*d, 2511f*d, 944f*d, arrayOf(14)),
            HitBox(2423f*d, 944f*d, 2494f*d, 978f*d, arrayOf(14)),
            HitBox(2200f*d, 870f*d, 2306f*d, 922f*d, arrayOf(9)),
            HitBox(2170f*d, 922f*d, 2310f*d, 980f*d, arrayOf(9)),
            HitBox(2155f*d, 980f*d, 2313f*d, 1040f*d, arrayOf(9)),
            HitBox(2183f*d, 1040f*d, 2248f*d, 1063f*d, arrayOf(9)),
            HitBox(1736f*d, 862f*d, 1925f*d, 926f*d, arrayOf(5)),
            HitBox(1472f*d, 818f*d, 1636f*d, 857f*d, arrayOf(2,3,4,6,21)),
            HitBox(1390f*d, 859f*d, 1636f*d, 899f*d, arrayOf(2,3,4,6,21)),
            HitBox(1253f*d, 903f*d, 1635f*d, 916f*d, arrayOf(2,3,4,6,21)),
            HitBox(1159f*d, 918f*d, 1553f*d, 954f*d, arrayOf(2,3,4,6,21)),
            HitBox(1020f*d, 956f*d, 1489f*d, 1005f*d, arrayOf(2,3,4,6,21)),
            HitBox(1084f*d, 1013f*d, 1508f*d, 1056f*d, arrayOf(2,3,4,6,21)),
            HitBox(1121f*d, 1061f*d, 1508f*d, 1112f*d, arrayOf(2,3,4,6,21)),
            HitBox(1164f*d, 1114f*d, 1460f*d, 1157f*d, arrayOf(2,3,4,6,21)),
            HitBox(1233f*d, 1160f*d, 1329f*d, 1219f*d, arrayOf(2,3,4,6,21)),
            HitBox(878f*d, 1091f*d, 981f*d, 1139f*d, arrayOf(7)),
            HitBox(927f*d, 1159f*d, 1061f*d, 1197f*d, arrayOf(7)),
            HitBox(963f*d, 1197f*d, 1090f*d, 1237f*d, arrayOf(7)),
            HitBox(1023f*d, 1237f*d, 1120f*d, 1282f*d, arrayOf(7)),
            HitBox(1049f*d, 1262f*d, 1152f*d, 1308f*d, arrayOf(7)),
            HitBox(929f*d, 720f*d, 1142f*d, 852f*d, arrayOf(11)),
            HitBox(509f*d, 781f*d, 629f*d, 850f*d, arrayOf(36)),
            HitBox(346f*d, 1017f*d, 437f*d, 1135f*d, arrayOf(1)),
            HitBox(376f*d, 1006f*d, 486f*d, 1087f*d, arrayOf(1)),
            HitBox(430f*d, 984f*d, 545f*d, 1057f*d, arrayOf(1)),
            HitBox(463f*d, 964f*d, 595f*d, 1031f*d, arrayOf(1)),
            HitBox(501f*d, 946f*d, 661f*d, 997f*d, arrayOf(1)),
            HitBox(553f*d, 917f*d, 708f*d, 973f*d, arrayOf(1)),
            HitBox(661f*d, 864f*d, 894f*d, 940f*d, arrayOf(1)),
            HitBox(717f*d, 832f*d, 862f*d, 862f*d, arrayOf(1)),
            HitBox(760f*d, 795f*d, 824f*d, 831f*d, arrayOf(1)),
            HitBox(442f*d, 1189f*d, 677f*d, 1400f*d, arrayOf(8)),
            HitBox(584f*d, 1334f*d, 818f*d, 1484f*d, arrayOf(8)),
            HitBox(459f*d, 1426f*d, 612f*d, 1627f*d, arrayOf(12)),
            HitBox(950f*d, 1538f*d, 1068f*d, 1562f*d, arrayOf(23)),
            HitBox(877f*d, 1563f*d, 1100f*d, 1616f*d, arrayOf(23)),
            HitBox(789f*d, 1616f*d, 1168f*d, 1749f*d, arrayOf(23)),
            HitBox(1308f*d, 1294f*d, 1450f*d, 1346f*d, arrayOf(17)),
            HitBox(1183f*d, 1366f*d, 1503f*d, 1499f*d, arrayOf(17)),
            HitBox(1275f*d, 1500f*d, 1379f*d, 1574f*d, arrayOf(17)),
            HitBox(1379f*d, 1519f*d, 1585f*d, 1653f*d, arrayOf(17)),
            HitBox(1896f*d, 1520f*d, 2113f*d, 1706f*d, arrayOf(18)),
            HitBox(1830f*d, 1727f*d, 1948f*d, 1867f*d, arrayOf(15)),
            HitBox(2300f*d, 1462f*d, 2656f*d, 1765f*d, arrayOf(37)), // 되다;

            HitBox(1763f*d, 305f*d, 1799f*d, 330f*d),
            HitBox(1779f*d, 319f*d, 1848f*d, 343f*d),
            HitBox(1776f*d, 343f*d, 1878f*d, 367f*d),
            HitBox(1848f*d, 366f*d, 1913f*d, 414f*d),
            HitBox(1618f*d, 428f*d, 1771f*d, 481f*d),
            HitBox(1617f*d, 606f*d, 1674f*d, 697f*d),
            HitBox(1766f*d, 718f*d, 1832f*d, 781f*d),
            HitBox(1833f*d, 757f*d, 1869f*d, 795f*d),
            HitBox(1869f*d, 777f*d, 1905f*d, 813f*d),
            HitBox(1906f*d, 806f*d, 1927f*d, 824f*d),
            HitBox(2315f*d, 979f*d, 2381f*d, 1024f*d),
            HitBox(2019f*d, 975f*d, 2055f*d, 995f*d),
            HitBox(1978f*d, 996f*d, 2071f*d, 1064f*d),
            HitBox(1923f*d, 1022f*d, 1980f*d, 1055f*d),
            HitBox(2071f*d, 1025f*d, 2096f*d, 1049f*d),
            HitBox(2070f*d, 1050f*d, 2125f*d, 1091f*d),
            HitBox(2239f*d, 1083f*d, 2375f*d, 1101f*d),
            HitBox(1816f*d, 1089f*d, 1979f*d, 1210f*d),
            HitBox(1831f*d, 1154f*d, 2165f*d, 1249f*d),
            HitBox(2232f*d, 1151f*d, 2282f*d, 1232f*d),
            HitBox(2321f*d, 1136f*d, 2387f*d, 1192f*d),
            HitBox(2133f*d, 1225f*d, 2196f*d, 1410f*d),
            HitBox(2151f*d, 1397f*d, 2186f*d, 1522f*d),
            HitBox(2135f*d, 1436f*d, 2151f*d, 1489f*d),
            HitBox(2266f*d, 1233f*d, 2311f*d, 1358f*d),
            HitBox(2353f*d, 1195f*d, 2378f*d, 1382f*d),
            HitBox(2178f*d, 1522f*d, 2189f*d, 1672f*d),
            HitBox(2430f*d, 1357f*d, 2668f*d, 1462f*d),
            HitBox(2220f*d, 1663f*d, 2336f*d, 1830f*d),
            HitBox(1948f*d, 1707f*d, 2111f*d, 1866f*d),
            HitBox(1856f*d, 1871f*d, 2156f*d, 1937f*d),
            HitBox(1887f*d, 1937f*d, 1950f*d, 1990f*d),
            HitBox(2352f*d, 1854f*d, 2435f*d, 1898f*d),
            HitBox(2466f*d, 1832f*d, 2704f*d, 1860f*d),
            HitBox(1532f*d, 1197f*d, 1692f*d, 1238f*d),
            HitBox(1452f*d, 1239f*d, 1714f*d, 1303f*d),
            HitBox(1590f*d, 1305f*d, 1730f*d, 1482f*d),
            HitBox(1713f*d, 1391f*d, 1755f*d, 1484f*d),
            HitBox(1588f*d, 1484f*d, 1778f*d, 1543f*d),
            HitBox(1739f*d, 1543f*d, 1779f*d, 1628f*d),
            HitBox(1372f*d, 1661f*d, 1417f*d, 1698f*d),
            HitBox(1438f*d, 1715f*d, 1500f*d, 1782f*d),
            HitBox(1487f*d, 1764f*d, 1555f*d, 1838f*d),
            HitBox(1343f*d, 1790f*d, 1392f*d, 1842f*d),
            HitBox(1378f*d, 1821f*d, 1434f*d, 1888f*d),
            HitBox(1425f*d, 1865f*d, 1487f*d, 1934f*d),
            HitBox(1466f*d, 1899f*d, 1532f*d, 1982f*d),
            HitBox(1504f*d, 1917f*d, 1568f*d, 2030f*d),
            HitBox(1391f*d, 1971f*d, 1417f*d, 2024f*d),
            HitBox(1384f*d, 2027f*d, 1463f*d, 2052f*d),
            HitBox(1170f*d, 1707f*d, 1232f*d, 1754f*d),
            HitBox(1092f*d, 1750f*d, 1178f*d, 1779f*d),
            HitBox(1096f*d, 1781f*d, 1134f*d, 1800f*d),
            HitBox(938f*d, 1804f*d, 1060f*d, 1828f*d),
            HitBox(970f*d, 1831f*d, 1009f*d, 1854f*d),
            HitBox(300f*d, 1256f*d, 438f*d, 1542f*d),
            HitBox(527f*d, 1629f*d, 789f*d, 1693f*d),
            HitBox(1110f*d, 1416f*d, 1182f*d, 1475f*d),
            HitBox(960f*d, 1500f*d, 1076f*d, 1534f*d)

        ))

        return hitCtl
    }
}