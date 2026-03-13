package cl.prezdev.port;

import cl.prezdev.model.response.AddAvlResponse;
import cl.prezdev.model.response.ListAvlResponse;
import cl.prezdev.model.response.RemoveAllResponse;
import cl.prezdev.model.response.StartAllResponse;
import cl.prezdev.model.response.StatResponse;
import cl.prezdev.model.response.StopAvlResponse;

public interface AvlClientPort {
    AddAvlResponse addAvls(String type, int count);

    StatResponse getStats();
    
    RemoveAllResponse removeAll();
    
    ListAvlResponse listAvls(Integer page, Integer size);
    
    StartAllResponse startAll();

    StopAvlResponse stopAll();
}
