package ncu.cc.commons.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.GsonBuilder;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Mac Liu (linuzilla@gmail.com)
 * @version 1.0
 * @since 1.0
 */
public class StackTraceUtil {
	private static Set<String> skipSet;
	private static Set<String> stopSet;
	private static ObjectMapper objectMapper;

	static {
		skipSet = new HashSet<String>();
		skipSet.add(StackTraceUtil.class.getName());

		stopSet = new HashSet<String>();

		objectMapper = new ObjectMapper();
	}

	public static void print1(Object message) {
//		System.out.println((message instanceof String) ? message : new GsonBuilder().setPrettyPrinting().create().toJson(message));
		try {
			System.out.println((message instanceof String) ? message : objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(message));
		} catch (JsonProcessingException e) {
			System.out.println(message.toString());
		}

		for (StackTraceElement ste: new Throwable().getStackTrace()) {
			if (skipSet.contains(ste.getClassName())) continue;
			System.out.println("        at " +
					ste.getClassName() + "." + 
					ste.getMethodName() + 
					"(" + ste.getFileName() + ":" 
					+ ste.getLineNumber() + ")");
			break;
		}
	}
	
	public static void print(Object message) {
		print(message, 20);
	}
	
	public static void print(Object message, int depth) {
		System.out.println((message instanceof String) ? message : new GsonBuilder().setPrettyPrinting().create().toJson(message));
		
		int	i = 0;
		for (StackTraceElement ste: new Throwable().getStackTrace()) {
			if (skipSet.contains(ste.getClassName())) continue;
			
			System.out.println("        at " +
					ste.getClassName() + "." + 
					ste.getMethodName() + 
					"(" + ste.getFileName() + ":" 
					+ ste.getLineNumber() + ")");
			if (++i >= depth) break;
			
			if (stopSet.contains(ste.getClassName())) break;
		}
	}
}
