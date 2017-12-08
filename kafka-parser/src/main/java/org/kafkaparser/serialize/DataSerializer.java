package org.kafkaparser.serialize;

import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

public class DataSerializer<T> implements org.apache.kafka.common.serialization.Serializer<T>{

	@Override
	public void close() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void configure(@SuppressWarnings("rawtypes") Map arg0, boolean arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public byte[] serialize(String arg0, Object arg1) {
		byte[] retVal = null;
	    ObjectMapper objectMapper = new ObjectMapper();
	    try {
	      retVal = objectMapper.writeValueAsString(arg1).getBytes();
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
	    return retVal;
	}



}
