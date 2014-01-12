package br.com.test.ranking.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import br.com.test.ranking.exceptions.InfoException;

public class DateParser {

	private static final String DATE_PATTERN = "dd/MM/yyyy hh:mm:ss";
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_PATTERN);
	
	public static Date readDate(String date) throws InfoException{
		try {
			return dateFormat.parse(date);
		} catch (ParseException ex) {
			String message = "Problema ao ler a data: " + date;
			throw new InfoException(message, ex);
		}
	}
	
	public static String format( Date date ){
		return dateFormat.format(date);
	}
}
