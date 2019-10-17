package server;

import mumi.Mumi;

import java.io.Serializable;
import java.util.TreeSet;
import java.util.Vector;
import java.util.stream.Collectors;


public class Response implements Serializable {
    private String doings;
    private Vector<Mumi> mumis;

    public Response(String doings, Vector<Mumi> mumis){
        this.doings = doings;
        setMumis(mumis);
    }

    public String getDoings() {
        return doings;
    }

    public void setDoings(String doings) {
        this.doings = doings;
    }

    public Vector<Mumi> getMumis() {
        return mumis;
    }

    public void setMumis(Vector<Mumi> mumis) {
        this.mumis = mumis.stream()
                .collect(Collectors.toCollection(Vector::new));
    }
}