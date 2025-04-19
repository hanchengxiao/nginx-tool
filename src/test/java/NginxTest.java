import com.ohan.tool.nginx.NginxConfigLoader;
import com.ohan.tool.nginx.block.Block;
import com.ohan.tool.nginx.block.NginxConfig;
import com.ohan.tool.nginx.exception.EditParamException;
import com.ohan.tool.nginx.exception.ReadOsTypeException;

import java.io.IOException;
import java.text.ParseException;

public class NginxTest {
    public static void main(String[] args) throws ReadOsTypeException, EditParamException, IOException, ParseException {
        NginxConfig config = NginxConfigLoader.load("/Users/hcx/Desktop/WORK_SPACE/nginx-tool/src/test/java/nginx.conf");
        Block block = config.getChild("http","").getChildByName("localhost").getChildByName("/");
        System.out.println(config);
    }
}
