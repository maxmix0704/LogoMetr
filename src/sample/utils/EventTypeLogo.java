package sample.utils;

public enum EventTypeLogo {
    PANE(1), LOGO(2);

    private final int id;

    EventTypeLogo(int id){
        this.id = id;
    }

    public int getId(){
        return id;
    }

    public static EventTypeLogo getEventType(String s){
        if (s.contains("LOGO"))
            return EventTypeLogo.LOGO;
        else
            return EventTypeLogo.PANE;

    }
}
