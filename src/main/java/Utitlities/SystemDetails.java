package Utitlities;

import java.util.Map;

public class SystemDetails {

    public String getSystemName()
    {
        Map<String, String> env = System.getenv();
        if(env.containsKey("COMPUTERNAME"))
        {
            return env.get("COMPUTERNAME");
        }
        else
        {
            return "Unknown Host";
        }
    }

    public String getUserName()
    {
        Map<String, String> env = System.getenv();
        if(env.containsKey("USERNAME"))
        {
            return env.get("USERNAME");
        }
        else
        {
            return "Unknown User";
        }
    }

}
