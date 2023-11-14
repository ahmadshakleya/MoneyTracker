package employee;

public class Person
{
    private String name;
    private String function;

    public Person(String name, String function)
    {
        this.name = name;
        this.function = function;
    }

    public String getName()
    {
        return name;
    }

    public String getFunction()
    {
        return function;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setFunction(String function)
    {
        this.function = function;
    }
}
