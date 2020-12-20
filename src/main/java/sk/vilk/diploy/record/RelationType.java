package sk.vilk.diploy.record;

public enum RelationType {
    ONE_TO_ONE(SerialType.ONE_TO_ONE),
    ONE_TO_MANY(SerialType.ONE_TO_MANY),
    MANY_TO_ONE(SerialType.MAN__TO_ONE);

    private SerialType serialType;

    RelationType(SerialType serialType) {
        this.serialType = serialType;
    }

    public SerialType getSerialType() {
        return this.serialType;
    }
}
