package example.flowr.mock;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

import org.flowr.framework.core.promise.phase.ChunkValue;



public interface Mock {

	public interface MOCK_REQUEST{
		
	}
	
	public class PROMISE_MOCK_REQUEST implements MOCK_REQUEST{
		
		private String request 				= "id:101";	
		private boolean verified			= false;
		private String status				= "INITIATED";
		
		public String toString(){
			
			return "PROMISE_MOCK_REQUEST | "+request+" | "+status+" | "+verified;
		}
	}
	
	public class PHASED_PROMISE_MOCK_REQUEST implements MOCK_REQUEST{
		
		String request = "id:10101";
		
		String eventBooth = "Product Launch";
		
		String nodesInNetwork = "Streaming Nodes In network";
		
		public String toString(){
			
			return "PHASED_PROMISE_MOCK_REQUEST |"+request+" | "+eventBooth+" | "+nodesInNetwork;
		}
	}
	
	public class SCHEDULED_PROMISE_MOCK_REQUEST implements MOCK_REQUEST{
		
		private String request 				= "ReportType : Daily | Template:202";	
		private Timestamp requestTime		= Timestamp.from(Instant.now());
		private String input				= "k=2;v:5;s=20;n=54";

		
		public String toString(){
			
			return "SCHEDULED_PROMISE_MOCK_REQUEST | "+request+" | "+requestTime+" | "+input;
		}
	}
	
	public interface MOCK_RESPONSE{
		
	}
	
	public class PROMISE_MOCK_RESPONSE implements MOCK_RESPONSE{
		
		private String response 			= "id=101:value=202";
		private boolean verified			= true;
		private String status				= "OK";
		
		public String toString(){
			
			return "PROMISE_MOCK_RESPONSE | "+response+" | "+status+" | "+verified;
		}
	}
	
	public class PHASED_PROMISE_MOCK_RESPONSE implements MOCK_RESPONSE{
		
		String response 		= "id=101:value=202";
		int numberOfAttendees 	= 1000000;
		int numberOfNodes		= 350;
		
		public String toString(){
			
			return "PHASED_PROMISE_MOCK_RESPONSE | "+response+" | numberOfAttendees : "+numberOfAttendees+" | numberOfNodes : "+numberOfNodes;
		}
	}
	
	public class SCHEDULED_PROMISE_MOCK_RESPONSE implements MOCK_RESPONSE{
		
		private String request 				= "ReportType : Daily | Template:202";	
		private Timestamp requestTime		= Timestamp.from(Instant.now());
		private String attachment			= "Report_"+requestTime+".xls";

		
		public String toString(){
			
			return "SCHEDULED_PROMISE_MOCK_RESPONSE | "+request+" | "+attachment;
		}
	}
	
	public class MOCK_CHUNKED_RESPONSE   implements MOCK_RESPONSE,ChunkValue{

		private Queue<String> chunkQueue 	= new ConcurrentLinkedDeque<String>();
		private long streamStartTimestamp 	= 0;
			
		@Override
		public void appendChunk(byte[] chunk) {
			
			if(streamStartTimestamp == 0){
				
				streamStartTimestamp 	= System.currentTimeMillis();
			}
					
			//System.out.println("chunk added : "+new String(chunk));
			chunkQueue.add(new String(chunk));
		}
		@Override
		public Object[] asStream() {
					
			return  chunkQueue.toArray();
		}
		@Override
		public int compareTo(Delayed o) {
			// TODO Auto-generated method stub
			return 0;
		}
		@Override
		public long getDelay(TimeUnit unit) {
			return (System.currentTimeMillis() - streamStartTimestamp);
		}
		@Override
		public boolean isQoSApplicable() {
			// TODO Auto-generated method stub
			return false;
		}
		@Override
		public byte[] getChunkStartIndex() {
			// TODO Auto-generated method stub
			return null;
		}
		@Override
		public void setChunkStartIndex(byte[] chunkStartIndex) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public byte[] getChunkLength() {
			// TODO Auto-generated method stub
			return null;
		}
		@Override
		public void setChunkLength(byte[] chunkLength) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public byte[] getChunkEndIndex() {
			// TODO Auto-generated method stub
			return null;
		}
		@Override
		public void setChunkData(byte[] chunkData) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public byte[] getChunkData() {
			// TODO Auto-generated method stub
			return null;
		}
		
		public String toString(){
			
			//String[] res = asStream();
			
			StringBuffer buffer = new StringBuffer();
					
			Iterator<String> chunkIterator = chunkQueue.iterator();
			
			while(chunkIterator.hasNext()){
				
				buffer.append(chunkIterator.next());
			}
			
			return buffer.toString();
		}
	}
	
}
