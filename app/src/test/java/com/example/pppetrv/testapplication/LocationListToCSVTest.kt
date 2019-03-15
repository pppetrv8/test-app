package com.example.pppetrv.testapplication

import com.example.pppetrv.testapplication.util.TestUtils
import com.example.pppetrv.testapplication.util.TimeUtil.SERVER_DATE_TIMESTAMP

import org.junit.Before
import org.junit.Test
import java.io.File
import java.lang.StringBuilder
import java.text.SimpleDateFormat
import java.util.*

class LocationListToCSVTest {

    var locationsStr: String? = null

    @Before
    public fun prepare() {
        locationsStr = TestUtils().readString("text/gps_path_crossroads_test.txt")
    }

    @Test
    public fun testLocationListToCSV() {
        assert(locationsStr != null)
        val strBuilder = StringBuilder("")
        val reader = locationsStr?.reader()
        var cnt = 0
        val cal = Calendar.getInstance()
        val timestamp = cal.time.time
        val format = SimpleDateFormat(SERVER_DATE_TIMESTAMP)
        reader?.forEachLine { s ->
            strBuilder.append(cnt).append(",")
            cal.timeInMillis = timestamp + 1000 * cnt
            strBuilder.append(s).append(",")
                .append("\"").append(format.format(cal.time)).append("\"")
                .append("\r\n")
            cnt++
        }
        File("../test_file.txt").writeText(strBuilder.toString())
    }
}

