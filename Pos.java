class Pos {
    public int i;
    public int j;
    public double distance;
    public Pos pre;
    public int sequence;
    public double dist_h;



    public Pos(int i, int j, double distance, Pos pre, int sequence, double dist_h) {
        this.i = i;
        this.j = j;
        this.distance = distance;
        this.pre = pre;
        this.sequence = sequence;
        this.dist_h = dist_h;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public int getJ() {
        return j;
    }

    public void setJ(int j) {
        this.j = j;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public Pos getPre() {
        return pre;
    }

    public void setPre(Pos pre) {
        this.pre = pre;
    }

    public double getDist_h() {
        return dist_h;
    }

    public void setDist_h(double dist_h) {
        this.dist_h = dist_h;
    }
}
