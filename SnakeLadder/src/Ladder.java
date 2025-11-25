public class Ladder {
    int start;
    int end;

    public Ladder(int start, int end) {
        if(start >= end){
            throw new IllegalArgumentException("Ladder must go up.");
        }
        this.start = start;
        this.end = end;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }
}
