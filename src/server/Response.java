package server;

import mumi.Mumi;

import java.io.Serializable;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.stream.Collectors;


public class Response implements Serializable {
    private String doings;
    private ConcurrentSkipListSet<Mumi> mumis;

    public Response(String doings, ConcurrentSkipListSet<Mumi> mumis){
        this.doings = doings;
        setMumis(mumis);
    }

    public String getDoings() {
        return doings;
    }

    public void setDoings(String doings) {
        this.doings = doings;
    }

    public ConcurrentSkipListSet<Mumi> getMumis() {
        return mumis;
    }

    public void setMumis(ConcurrentSkipListSet<Mumi> mumis) {
        this.mumis = mumis.stream()
                .collect(Collectors.toCollection(ConcurrentSkipListSet::new));
    }
}