package mcp.mcpdemo.mcp;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class LeaveManagerService {
    
    // In-memory mock database
    private static final Map<String, EmployeeLeave> employeeLeaves = new HashMap<>();
    
    static {
        employeeLeaves.put("E001", new EmployeeLeave(18, 
            new ArrayList<>(Arrays.asList("2024-12-25", "2025-01-01"))));
        employeeLeaves.put("E002", new EmployeeLeave(20, new ArrayList<>()));
    }

    @Tool(description = "Check how many leave days are left for the employee")
    public String getLeaveBalance(String employeeId) {
        EmployeeLeave data = employeeLeaves.get(employeeId);
        if (data != null) {
            return employeeId + " has " + data.balance + " leave days remaining.";
        }
        return "Employee ID not found.";
    }

    @Tool(description = "Apply leave for specific dates")  
    public String applyLeave(String employeeId, List<String> leaveDates) {
        EmployeeLeave data = employeeLeaves.get(employeeId);
        if (data == null) {
            return "Employee ID not found.";
        }
        
        int requestedDays = leaveDates.size();
        if (data.balance < requestedDays) {
            return "Insufficient leave balance. You requested " + requestedDays + 
                   " day(s) but have only " + data.balance + ".";
        }
        
        data.balance -= requestedDays;
        data.history.addAll(leaveDates);
        
        return "Leave applied for " + requestedDays + " day(s). Remaining balance: " + 
               data.balance + ".";
    }

    @Tool(description = "Get leave history for the employee")
    public String getLeaveHistory(String employeeId) {
        EmployeeLeave data = employeeLeaves.get(employeeId);
        if (data != null) {
            if (data.history.isEmpty()) {
                return "No leaves taken.";
            } else {
                return "Leave history for " + employeeId + ": " + 
                       String.join(", ", data.history);
            }
        }
        return "Employee ID not found.";
    }
    
    static class EmployeeLeave {
        int balance;
        List<String> history;
        
        EmployeeLeave(int balance, List<String> history) {
            this.balance = balance;
            this.history = history;
        }
    }
}