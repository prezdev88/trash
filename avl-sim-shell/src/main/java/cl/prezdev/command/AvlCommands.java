package cl.prezdev.command;

import lombok.RequiredArgsConstructor;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.stereotype.Component;

import cl.prezdev.model.response.AddAvlResponse;
import cl.prezdev.model.response.ListAvlResponse;
import cl.prezdev.model.response.RemoveAllResponse;
import cl.prezdev.model.response.StartAllResponse;
import cl.prezdev.model.response.StatResponse;
import cl.prezdev.model.response.StopAvlResponse;
import cl.prezdev.port.AvlClientPort;

@Component
@ShellComponent
@RequiredArgsConstructor
public class AvlCommands {

    private final AvlClientPort avlClientPort;

    @ShellMethod(key = "avl add", value = "Adds N simulated devices of the given type (teltonika, queclink, etc.)")
    public String add(
        @ShellOption(help = "Device type") String type,
        @ShellOption(help = "Number of devices") int count
    ) {
        AddAvlResponse addAvlResponse = avlClientPort.addAvls(type, count);
        return "[OK] Added " + addAvlResponse.getCount() + " devices of type " + addAvlResponse.getType().toUpperCase();
    }

    @ShellMethod(key = "avl list", value = "Lists all active simulated devices.")
    public String list(
        @ShellOption(value = "--page", defaultValue = "1", help = "Page number (starting from 1)") Integer page,
        @ShellOption(value = "--size", defaultValue = "10", help = "Page size") Integer size
    ) {
        ListAvlResponse response = avlClientPort.listAvls(page - 1, size);
        
        if (response.getAvls().isEmpty()) {
            return "No AVL devices found.";
        }
        
        StringBuilder result = new StringBuilder();
        result.append("AVL Devices (Page ").append(response.getPage() + 1)
              .append(" of ").append(response.getTotalPages()).append("):").append(System.lineSeparator());
        result.append("Total Elements: ").append(response.getTotalElements()).append(System.lineSeparator()).append(System.lineSeparator());
        
        for (int i = 0; i < response.getAvls().size(); i++) {
            var avl = response.getAvls().get(i);
            result.append(String.format("%2d. ID: %d | IMEI: %s | Provider: %s | Started: %s%n",
                i + 1, avl.getId(), avl.getImei(), avl.getProvider(), 
                avl.isStarted() ? "YES" : "NO"));
        }
        
        result.append(System.lineSeparator()).append("Navigation: ");
        if (!response.isFirst()) {
            result.append(String.format("[Use --page %d] ← Previous | ", response.getPage()));
        }
        result.append("Current Page ").append(response.getPage() + 1).append(" of ").append(response.getTotalPages());
        if (!response.isLast()) {
            result.append(String.format(" | Next → [Use --page %d]", response.getPage() + 2));
        }
        
        return result.toString();
    }

    @ShellMethod(key = "avl stat", value = "Shows statistics of the current simulation.")
    public String stat() {
        StatResponse statResponse = avlClientPort.getStats();
        return "Active AVL devices: " + statResponse.getAvl();
    }

    @ShellMethod(key = "avl remove all", value = "Remove all devices")
    public String removeAll() {
        RemoveAllResponse response = avlClientPort.removeAll();
        return "[OK] Removed " + response.getRemoved() + " devices.";
    }

    @ShellMethod(key = "avl start", value = "Starts all simulated AVL devices.")
    public String startAll() {
        StartAllResponse response = avlClientPort.startAll();
        return String.format("[OK] %s", response.getMessage());
    }

    @ShellMethod(key = "avl stop", value = "Stops all simulated AVL devices.")
    public String stopAll() {
        StopAvlResponse response = avlClientPort.stopAll();
        return "[OK] " + response.getMessage();
    }
}
