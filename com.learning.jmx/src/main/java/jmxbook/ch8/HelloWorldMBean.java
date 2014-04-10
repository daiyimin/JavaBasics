package jmxbook.ch8;

public interface HelloWorldMBean
{
  public void setGreeting( String greeting );
  public String getGreeting();
  public void setCount(int count);
  public int getCount();
  
  public void queryMBean();
}

