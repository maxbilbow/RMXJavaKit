package click.rmx.debug.logger;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by Max on 19/12/2015.
 */
@ConfigurationProperties(prefix = "rmx.debug", ignoreUnknownFields = true)
public class DebugProperties{//} extends AbstractTemplateViewResolverProperties {

    private String color;


    public String getColor()
    {
        return color;
    }

    public void setColor(String color)
    {
        this.color = color;
    }
}
