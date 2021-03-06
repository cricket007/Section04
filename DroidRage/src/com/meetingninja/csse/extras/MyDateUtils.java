/*******************************************************************************
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package com.meetingninja.csse.extras;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import android.R;

public class MyDateUtils {
	/* JodaTime Formatter */
	public static final DateTimeFormatter ISO8601_FMT = ISODateTimeFormat
			.dateTime();
	public static final DateTimeFormatter JODA_SERVER_DATE_FORMAT = DateTimeFormat
			.forPattern("EEEE, dd-MMM-yy HH:mm:ss zzz").withZoneUTC();
//	DateTimeParser[] parsers = {
//	        DateTimeFormat.forPattern( "yyyy-MM-dd HH" ).getParser(),
//	        DateTimeFormat.forPattern( "yyyy-MM-dd" ).getParser() };
//	DateTimeFormatter formatter = new DateTimeFormatterBuilder().append( null, parsers ).toFormatter();

	public static final DateTimeFormatter JODA_APP_DATE_FORMAT = DateTimeFormat
			.forPattern("EEE, MMM dd, yyyy").withZoneUTC();
	public static final DateTimeFormatter JODA_24_TIME_FORMAT = DateTimeFormat
			.forPattern("HH:mm").withZoneUTC();
	public static final DateTimeFormatter JODA_12_TIME_FORMAT = DateTimeFormat
			.forPattern("hh:mma").withZoneUTC();

}
