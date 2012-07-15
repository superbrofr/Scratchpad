class Item
{
  private int dd, mm, yy;
  private int hours, mins;
  private String timePeriod;
  private String activity;
  
  public Item(String details)
  {
    String[] eventDetails = details.split(" ");
    initDate(eventDetails[0]);
    initTime(eventDetails[1]);
    initActivity(eventDetails);
  }
  
  private void initDate(String date)
  {
    String[] dateSplit = date.split("/");
    dd = Integer.parseInt(dateSplit[0]);
    mm = Integer.parseInt(dateSplit[1]);
    yy = Integer.parseInt(dateSplit[2].charAt(2) + "" + dateSplit[2].charAt(3));
  }
  
  private void initTime(String time)
  {
    String[] timeSplit = time.split(":");
    hours = Integer.parseInt(timeSplit[0]);
    if(timeSplit[1].contains("AM")){
      timePeriod = "AM";
      mins = Integer.parseInt(timeSplit[1].split("AM")[0]);
    }
    else{
      timePeriod = "PM";
      mins = Integer.parseInt(timeSplit[1].split("PM")[0]);
    }
  }
  
  private void initActivity(String[] eventDetails)
  {
    String act = "";
    for(int i = 2; i < eventDetails.length; i++){
      act += eventDetails[i] + " ";
    }
    activity = act.trim();
  }
  
  public String getDate()
  {
    String re = "";
    re += dd + "/" + mm + "/" + yy;
    return re;
  }
  
  public String getTime()
  {
    String re = "";
    re += hours + ":" + mins;
    if(mins < 10)
      re += "0";
    re += timePeriod;
    return re;
  }
  
  public String getAct()
  {
    return activity;
  }
  
  public String getItemDeets()
  {
    return getDate() + " " + getTime() + " " + getAct();
  }
  
  public int getMonth()
  {
    return mm;
  }
  
  public int getDay()
  {
    return dd;
  }
  
  public int getYear()
  {
    return yy;
  }
  
  public int getHours()
  {
    return hours;
  }
  
  public int getMins()
  {
    return mins;
  }
  
  public String getTimePeriod()
  {
    return timePeriod;
  }
}