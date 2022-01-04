package ncu.cc.digger.constants;

/**
 * @author Mac Liu (linuzilla@gmail.com)
 * @version 1.0
 * @since 1.0
 */
public class CronValues {
    public static final String EVERY_10_SECONDS      = "*/10 *  * * * *";
    public static final String EVERY_MINUTES         = "0    *  * * * *";
    public static final String EVERY_OTHER_MINUTES   = "0  */2  * * * *";
    public static final String EVERY_FIVE_MINUTES    = "0  */5  * * * *";
    public static final String EVERY_TEN_MINUTES     = "0 */10  * * * *";
    public static final String EVERY_DAY             = "0    0  0 * * *";
    public static final String EVERY_MONTH           = "0    0  0 1 * *";
    public static final String EVERY_YEAR            = "0    0  0 1 1 *";
    public static final String RETRIEVE_TEAM_DRIVE   = "0   10  2 1 * *"; // Every Month (on 1st day of the Month 2:10am)
    public static final String NOTIFY_BEFORE_SUSPEND = "0    0  6 1 * *"; // Every Month (on 1st day of the Month 6:00am)
    public static final String SUSPEND_ACCOUNT       = "0    0  6 15 * *"; // Every Month (on 15th day of the Month 6:00am)
    public static final String TERMINATE_ACCOUNT     = "0   45 23  * * *"; // Every Day
    public static final String REPORT_GATHERING      = "0   55 23  * * SUN";
}
