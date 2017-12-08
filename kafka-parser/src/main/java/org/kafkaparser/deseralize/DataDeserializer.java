package org.kafkaparser.deseralize;

import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.kafkaparser.pojo.Data;

@SuppressWarnings("unchecked")
public class DataDeserializer<T> implements org.apache.kafka.common.serialization.Deserializer<T>{

	@Override
	public T deserialize(String var1, byte[] arg1) {
		ObjectMapper mapper = new ObjectMapper();
		Data data=null;
		
		try {
		      data = mapper.readValue(arg1, Data.class);
		    } catch (Exception e) {

		      e.printStackTrace();
		    }
		return (T) data;
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void configure(@SuppressWarnings("rawtypes") Map arg0, boolean arg1) {
		// TODO Auto-generated method stub
		
	}


}
