package com.chaychan.news.utils

import java.math.BigDecimal
import java.math.BigInteger
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

//空处理=============================================================
fun String?.nul() = if (null == this) "" else this

fun Int?.nul() = if (null == this) 0 else this

fun Double?.nul() = if (null == this) 0.0 else this

fun Short?.nul() = if (null == this) 0 else this

fun Long?.nul() = if (null == this) 0 else this

fun Byte?.nul() = if (null == this) 0 else this

fun Float?.nul() = if (null == this) 0 else this

fun BigDecimal?.nul() = if (null == this) BigDecimal.ZERO else this

fun BigInteger?.nul() = if (null == this) BigInteger.ZERO else this
//空处理=============================================================

//日期格式化
fun String.parseDate(format: String) = SimpleDateFormat(format).parse(this)
fun format(format: String,date:Date) = SimpleDateFormat(format).format(date)
fun now(format: String) = SimpleDateFormat(format).format(Date())
fun format(format: String, time: Long) = SimpleDateFormat(format).format(Date(time))

fun String?.toMoney() = DecimalFormat.getCurrencyInstance().format((this ?: "0").toDouble())
fun String?.toMoney(format: String) = DecimalFormat(format).format((this ?: "0").toDouble())

fun Double?.toMoney() = DecimalFormat.getCurrencyInstance().format((this ?: 0.0))
fun Double?.toMoney(format: String) = DecimalFormat(format).format((this ?: 0.0))

fun page(pageNo:Int,pageSize:Int) = (pageNo-1)*pageSize to pageSize