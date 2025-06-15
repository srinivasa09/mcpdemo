package mcp.mcpdemo;

import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import mcp.mcpdemo.mcp.LeaveManagerService;

@SpringBootApplication
public class LeaveManagerMcpServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(LeaveManagerMcpServerApplication.class, args);
    }

    @Bean
    public ToolCallbackProvider leaveManagerTools(LeaveManagerService leaveManagerService) {
        return MethodToolCallbackProvider.builder()
                .toolObjects(leaveManagerService)
                .build();
    }
}