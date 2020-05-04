
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package example.flowr.mock;

import java.nio.charset.Charset;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

import org.flowr.framework.core.model.Model.RequestModel;
import org.flowr.framework.core.model.Model.ResponseModel;
import org.flowr.framework.core.notification.Notification.ClientNotificationProtocolType;
import org.flowr.framework.core.notification.Notification.NotificationDeliveryType;
import org.flowr.framework.core.notification.Notification.NotificationFormatType;
import org.flowr.framework.core.notification.Notification.NotificationFrequency;
import org.flowr.framework.core.notification.Notification.NotificationProtocolType;
import org.flowr.framework.core.notification.Notification.NotificationType;
import org.flowr.framework.core.notification.Notification.ServerNotificationProtocolType;
import org.flowr.framework.core.notification.subscription.EventNotificationSubscription;
import org.flowr.framework.core.notification.subscription.NotificationSubscription;
import org.flowr.framework.core.notification.subscription.NotificationSubscription.SubscriptionOption;
import org.flowr.framework.core.notification.subscription.NotificationSubscription.SubscriptionType;
import org.flowr.framework.core.promise.phase.ChunkValue;



public interface Mock {

    public interface MockRequest extends RequestModel{

    }

    public class PromiseMockRequest implements MockRequest{

        private String request              = "id:101"; 
        private boolean verified;
        private String status               = "INITIATED";

        public String toString(){

            return "PromiseMockRequest | "+request+" | "+status+" | "+verified;
        }
    }

    public class PhasedPromiseMockRequest implements MockRequest{

        private String request = "id:10101";

        private String eventBooth = "Product Launch";

        private String nodesInNetwork = "Streaming Nodes In network";

        public String toString(){

            return "PhasedPromiseMockRequest |"+request+" | "+eventBooth+" | "+nodesInNetwork;
        }
    }

    public class ScheduledPromiseMockRequest implements MockRequest{

        private String request              = "ReportType : Daily | Template:202";  
        private Timestamp requestTime       = Timestamp.from(Instant.now());
        private String input                = "k=2;v:5;s=20;n=54";


        public String toString(){

            return "ScheduledPromiseMockRequest | "+request+" | "+requestTime+" | "+input;
        }
    }

    public interface MockResponse extends ResponseModel{

    }

    public class PromiseMockResponse implements MockResponse{

        private String response             = "id=101:value=202";
        private boolean verified            = true;
        private String status               = "OK";

        public String toString(){

            return "PromiseMockResponse | "+response+" | "+status+" | "+verified;
        }
    }

    public class PhasedPromiseMockResponse implements MockResponse{

        private String response         = "id=101:value=202";
        private int numberOfAttendees   = 1000000;
        private int numberOfNodes       = 350;

        public String toString(){

            return "PhasedPromiseMockResponse | "+response+" | numberOfAttendees : "+numberOfAttendees+
                    " | numberOfNodes : "+numberOfNodes;
        }
    }

    public class ScheduledPromiseMockResponse implements MockResponse{

        private String request              = "ReportType : Daily | Template:202";  
        private Timestamp requestTime       = Timestamp.from(Instant.now());
        private String attachment           = "Report_"+requestTime+".xls";


        public String toString(){

            return "ScheduledPromiseMockResponse | "+request+" | "+attachment;
        }
    }

    public class MockChunkedResponse   implements MockResponse,ChunkValue{

        private Queue<String> chunkQueue    = new ConcurrentLinkedDeque<>();
        private long streamStartTimestamp;
        private byte[] chunkStartIndex;
        private byte[] chunkLength;
        private byte[] chunkData;

        @Override
        public void appendChunk(byte[] chunk) {

            if(streamStartTimestamp == 0){

                streamStartTimestamp    = System.currentTimeMillis();
            }
            chunkQueue.add(new String(chunk, Charset.defaultCharset()));
        }
        @Override
        public Object[] asStream() {

            return  chunkQueue.toArray();
        }
        @Override
        public int compareTo(Delayed o) {
            return 0;
        }
        @Override
        public long getDelay(TimeUnit unit) {
            return (System.currentTimeMillis() - streamStartTimestamp);
        }
        @Override
        public boolean isQoSApplicable() {
            return false;
        }
        @Override
        public byte[] getChunkStartIndex() {
            return new byte[] {0};
        }
        @Override
        public void setChunkStartIndex(byte[] chunkStartIndex) {

            this.chunkStartIndex = chunkStartIndex.clone();
        }
        @Override
        public byte[] getChunkLength() {
            return this.chunkLength.clone();
        }
        @Override
        public void setChunkLength(byte[] chunkLength) {

            this.chunkLength = chunkLength.clone();
        }
        @Override
        public byte[] getChunkEndIndex() {

            return this.chunkStartIndex.clone();
        }
        @Override
        public void setChunkData(byte[] chunkData) {
            this.chunkData = chunkData.clone();
        }
        @Override
        public byte[] getChunkData() {
            return this.chunkData.clone();
        }

        public String toString(){

            StringBuilder buffer = new StringBuilder();

            Iterator<String> chunkIterator = chunkQueue.iterator();

            while(chunkIterator.hasNext()){

                buffer.append(chunkIterator.next());
            }

            return buffer.toString();
        }
    }
    
    public static final class MockSettings{
        
        private MockSettings() {
            
        }
        
        public static  Map<NotificationProtocolType,NotificationSubscription> clientSubscriptionMap(){

            Map<NotificationProtocolType,NotificationSubscription> notificationSubscriptionMap = 
                    new HashMap<>();

            NotificationSubscription notificationSubscription = new EventNotificationSubscription();

            notificationSubscription.setSubscriptionType(SubscriptionType.CLIENT);
            notificationSubscription.setNotificationFormatType(NotificationFormatType.TEXT);

            notificationSubscription.setNotificationFrequency(NotificationFrequency.ALL);

            notificationSubscription.setNotificationProtocolType(ClientNotificationProtocolType.CLIENT_EMAIL);

            notificationSubscription.setNotificationType(NotificationType.AMQP);
            notificationSubscription.setNotificationDeliveryType(NotificationDeliveryType.EXTERNAL);
            notificationSubscription.setSubscriptionOption(SubscriptionOption.AUTOMATIC);

            notificationSubscriptionMap.put(ClientNotificationProtocolType.CLIENT_EMAIL, notificationSubscription);

            return notificationSubscriptionMap; 
        }

        public static Map<NotificationProtocolType,NotificationSubscription> serverSubscriptionMap(){

            Map<NotificationProtocolType,NotificationSubscription> notificationSubscriptionMap = new HashMap<>();

            NotificationSubscription notificationSubscription = new EventNotificationSubscription();

            notificationSubscription.setSubscriptionType(SubscriptionType.SERVER);
            notificationSubscription.setNotificationFormatType(NotificationFormatType.TEXT);

            notificationSubscription.setNotificationFrequency(NotificationFrequency.ALL);

            notificationSubscription.setNotificationProtocolType(ServerNotificationProtocolType.SERVER_ALL);

            notificationSubscription.setNotificationType(NotificationType.AMQP);
            notificationSubscription.setNotificationDeliveryType(NotificationDeliveryType.INTERNAL);
            notificationSubscription.setSubscriptionOption(SubscriptionOption.AUTOMATIC);

            notificationSubscriptionMap.put(ServerNotificationProtocolType.SERVER_ALL, notificationSubscription);

            return notificationSubscriptionMap;
        }
    }

}
