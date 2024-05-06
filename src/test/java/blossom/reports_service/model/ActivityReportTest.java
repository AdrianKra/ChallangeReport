package blossom.reports_service.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Date;
import static org.mockito.BDDMockito.given;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class ActivityReportTest {

  @Mock
  private Activity activity;

  @Mock
  private User user;

  java.util.Date date = new Date();

  @Test
  public void testActivityReport() {
    ActivityReport activityReport = new ActivityReport(activity, user.getId(), "Activity Report 1", date, date,
        "John Doe", "Activity Report Description");

    given(activity.getName()).willReturn("Activity 1");
    given(activity.getDescription()).willReturn("Activity Description");
    given(activity.getStartDate()).willReturn(date);
    given(activity.getEndDate()).willReturn(date);

    given(user.getId()).willReturn(1L);

    assertEquals(1L, activityReport.getId());
    assertNotNull(activityReport.getActivity());
    assertEquals("Activity Report 1", activityReport.getName());
    assertNotNull(activityReport.getStartDate());
    assertNotNull(activityReport.getEndDate());
    assertEquals("John Doe", activityReport.getCreatedBy());
    assertEquals("Activity Report Description", activityReport.getDescription());
    assertEquals(ActivityStatus.OPEN, activityReport.getStatus());

    assertEquals("Activity 1", activity.getName());
    assertEquals("Activity Description", activity.getDescription());
    assertEquals(date, activity.getStartDate());
    assertEquals(date, activity.getEndDate());
  }

  // @Test
  // public void testActivitySummary() {
  // ActivitySummary activitySummary = new ActivitySummary();

  // activitySummary.setId(1L);
  // activitySummary.setActivity(new ArrayList<Activity>());
  // activitySummary.setName("Activity Summary 1");
  // activitySummary.setDescription("Activity Summary Description");
  // activitySummary.setStatus(ActivityStatus.OPEN);

  // assertEquals(1L, activitySummary.getId());
  // assertNotNull(activitySummary.getActivity());
  // assertEquals("Activity Summary 1", activitySummary.getName());
  // assertEquals("Activity Summary Description",
  // activitySummary.getDescription());
  // assertEquals(ActivityStatus.OPEN, activitySummary.getStatus());

  // }

  @Test
  public void testActivity() {
    Activity activity = new Activity();

    activity.setId(1L);
    activity.setName("Activity 1");
    activity.setDescription("Activity Description");
    activity.setStartDate(date);
    activity.setEndDate(date);

    assertEquals(1L, activity.getId());
    assertEquals("Activity 1", activity.getName());
    assertEquals("Activity Description", activity.getDescription());
    assertEquals(date, activity.getStartDate());
    assertEquals(date, activity.getEndDate());

  }
}
