
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package org.flowr.framework.core.notification.job;

import org.flowr.framework.core.security.policy.ScheduleCalendar;

public interface ScheduledNotificationDispatcher extends SubscriptionDispatcher{
    
    void setNotificationCalendar(ScheduleCalendar calendar);
}
