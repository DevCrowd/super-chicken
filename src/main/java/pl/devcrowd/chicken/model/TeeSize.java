package pl.devcrowd.chicken.model;

public enum TeeSize {
    XS("XS"),
    S("S"),
    M("M"),
    L("L"),
    XL("XL"),
    XXL("XXL");
    private String value;
    private TeeSize(String value) {
        this.value = value;
    }
    public String value() {
        return value;
    }
}
