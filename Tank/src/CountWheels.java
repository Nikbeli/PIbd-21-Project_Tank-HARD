public enum CountWheels {
    Two(2),
    Three(3),
    Four(4),
    Five(5);
    private final int Value;
    CountWheels(int Count){
        Value=Count;
    }
    public int getCountWheels(){
        return Value;
    }
    public int setCountWheels() { return Value; }
}
