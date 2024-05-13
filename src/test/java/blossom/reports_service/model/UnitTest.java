package blossom.reports_service.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Date;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class UnitTest {

  @Mock
  private Activity activity;

  @Mock
  private User user;

  java.util.Date date = new Date();

  @Test
  public void testActivityReport() {

    given(activity.getName()).willReturn("Activity 1");
    given(activity.getDescription()).willReturn("Activity Description");
    given(activity.getStartDate()).willReturn(date);
    given(activity.getEndDate()).willReturn(date);

    ActivityReport activityReport = new ActivityReport(activity, user, "Activity Report 1", date, "John Doe",
        "Activity Report Description");

    assertNotNull(activityReport.getActivity());
    assertEquals("Activity Report 1", activityReport.getName());
    assertNotNull(activityReport.getStartDate());
    assertNull(activityReport.getEndDate());
    assertEquals("John Doe", activityReport.getCreatedBy());
    assertEquals("Activity Report Description", activityReport.getDescription());
    assertEquals(ActivityStatus.OPEN, activityReport.getStatus());

    assertEquals("Activity 1", activity.getName());
    assertEquals("Activity Description", activity.getDescription());
    assertEquals(date, activity.getStartDate());
    assertEquals(date, activity.getEndDate());
  }

  @Test
  public void testActivitySummary() {
    ActivitySummary activitySummary = new ActivitySummary(user, new ArrayList<Activity>());

    assertEquals(user, user);
    assertNotNull(activitySummary.getActivitys());
    assertEquals(0, activitySummary.getActivityCount());
    assertEquals(0, activitySummary.getDoneCount());
    assertEquals(0, activitySummary.getPendingCount());
    assertEquals(0, activitySummary.getOverdueCount());
    assertEquals(0, activitySummary.getConsecutiveDays());
    assertEquals(0, activitySummary.getLongestStreak());

  }

  @Test
  public void testActivity() {
    Activity activity = new Activity("Activity 1", "Activity Description", date, date);

    assertEquals("Activity 1", activity.getName());
    assertEquals("Activity Description", activity.getDescription());
    assertEquals(date, activity.getStartDate());
    assertEquals(date, activity.getEndDate());
  }
}
