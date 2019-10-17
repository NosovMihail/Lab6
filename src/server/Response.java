package server;

import mumi.Mumi;

import java.io.Serializable;
import java.util.TreeSet;
import java.util.stream.Collectors;


public class Response implements Serializable {
    private String doings;
    private TreeSet<Mumi> mumis;

    public Response(String doings, TreeSet<Mumi> mumis){
        this.doings = doings;
        setMumis(mumis);
    }

    public String getDoings() {
        return doings;
    }

    public void setDoings(String doings) {
        this.doings = doings;
    }

    public TreeSet<Mumi> getMumis() {
        return mumis;
    }

    public void setMumis(TreeSet<Mumi> mumis) {
        this.mumis = mumis.stream()
                .sorted()
                .collect(Collectors.toCollection(TreeSet::new));
    }
}