package me.drex.world_gamerules.data;

import com.mojang.serialization.Codec;
import me.drex.world_gamerules.util.CCACompat;
import me.drex.world_gamerules.util.ModCompat;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.Difficulty;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedDataType;
import net.minecraft.world.level.storage.ServerLevelData;
import net.minecraft.world.level.timers.TimerQueue;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;
import java.util.function.Function;

public class SavedWorldLevelData extends SavedData implements ServerLevelData {

    private long dayTime;

    private int clearWeatherTime;
    private boolean raining;
    private int rainTime;
    private boolean thundering;
    private int thunderTime;

    public static final Function<Context, Codec<SavedWorldLevelData>> CODEC = context -> CompoundTag.CODEC.xmap(
        SavedWorldLevelData::load,
        SavedWorldLevelData::save);
    public static final SavedDataType<SavedWorldLevelData> TYPE = new SavedDataType<>("world_level_data", context -> SavedWorldLevelData.of(), CODEC, null);
    protected ServerLevelData parent;

    protected SavedWorldLevelData() {
    }

    public static SavedWorldLevelData of() {
        if (ModCompat.CARDINAL_COMPONENTS_LEVEL) {
            return CCACompat.create();
        }
        return new SavedWorldLevelData();
    }

    public void setParent(ServerLevelData parent) {
        this.parent = parent;
    }

    public CompoundTag save() {
        CompoundTag compoundTag = new CompoundTag();
        compoundTag.putLong("day_time", dayTime);
        compoundTag.putInt("clear_weather_time", clearWeatherTime);
        compoundTag.putBoolean("raining", raining);
        compoundTag.putInt("rain_time", rainTime);
        compoundTag.putBoolean("thundering", thundering);
        compoundTag.putInt("thunder_time", thunderTime);
        return compoundTag;
    }

    public static SavedWorldLevelData load(CompoundTag compoundTag) {
        SavedWorldLevelData data = SavedWorldLevelData.of();
        data.dayTime = compoundTag.getLongOr("day_time", 0);
        data.clearWeatherTime = compoundTag.getIntOr("clear_weather_time", 0);
        data.raining = compoundTag.getBooleanOr("raining", false);
        data.rainTime = compoundTag.getIntOr("rain_time", 0);
        data.thundering = compoundTag.getBooleanOr("thundering", false);
        data.thunderTime = compoundTag.getIntOr("thunder_time", 0);
        return data;
    }

    @Override
    public String getLevelName() {
        return parent.getLevelName();
    }

    @Override
    public void setThundering(boolean bl) {
        thundering = bl;
        setDirty();
    }

    @Override
    public int getRainTime() {
        return rainTime;
    }

    @Override
    public void setRainTime(int i) {
        this.rainTime = i;
        setDirty();
    }

    @Override
    public void setThunderTime(int i) {
        this.thunderTime = i;
        setDirty();
    }

    @Override
    public int getThunderTime() {
        return thunderTime;
    }

    @Override
    public int getClearWeatherTime() {
        return clearWeatherTime;
    }

    @Override
    public void setClearWeatherTime(int i) {
        this.clearWeatherTime = i;
        setDirty();
    }

    @Override
    public int getWanderingTraderSpawnDelay() {
        return parent.getWanderingTraderSpawnDelay();
    }

    @Override
    public void setWanderingTraderSpawnDelay(int i) {
        parent.setWanderingTraderSpawnDelay(i);
    }

    @Override
    public int getWanderingTraderSpawnChance() {
        return parent.getWanderingTraderSpawnChance();
    }

    @Override
    public void setWanderingTraderSpawnChance(int i) {
        parent.setWanderingTraderSpawnChance(i);
    }

    @Override
    public @Nullable UUID getWanderingTraderId() {
        return parent.getWanderingTraderId();
    }

    @Override
    public void setWanderingTraderId(UUID uUID) {
        parent.setWanderingTraderId(uUID);
    }

    @Override
    public GameType getGameType() {
        return parent.getGameType();
    }

    @Override
    public void setWorldBorder(WorldBorder.Settings settings) {
        parent.setWorldBorder(settings);
    }

    @Override
    public WorldBorder.Settings getWorldBorder() {
        return parent.getWorldBorder();
    }

    @Override
    public boolean isInitialized() {
        return parent.isInitialized();
    }

    @Override
    public void setInitialized(boolean bl) {
        parent.setInitialized(bl);
    }

    @Override
    public boolean isAllowCommands() {
        return parent.isAllowCommands();
    }

    @Override
    public void setGameType(GameType gameType) {
        parent.setGameType(gameType);
    }

    @Override
    public TimerQueue<MinecraftServer> getScheduledEvents() {
        return parent.getScheduledEvents();
    }

    @Override
    public void setGameTime(long l) {
        parent.setGameTime(l);
    }

    @Override
    public void setDayTime(long l) {
        this.dayTime = l;
        setDirty();
    }

    @Override
    public GameRules getGameRules() {
        return parent.getGameRules();
    }

    @Override
    public void setSpawn(BlockPos blockPos, float f) {
        parent.setSpawn(blockPos, f);
    }

    @Override
    public BlockPos getSpawnPos() {
        return parent.getSpawnPos();
    }

    @Override
    public float getSpawnAngle() {
        return parent.getSpawnAngle();
    }

    @Override
    public long getGameTime() {
        return parent.getGameTime();
    }

    @Override
    public long getDayTime() {
        return dayTime;
    }

    @Override
    public boolean isThundering() {
        return thundering;
    }

    @Override
    public boolean isRaining() {
        return raining;
    }

    @Override
    public void setRaining(boolean bl) {
        this.raining = bl;
        setDirty();
    }

    @Override
    public boolean isHardcore() {
        return parent.isHardcore();
    }

    @Override
    public Difficulty getDifficulty() {
        return parent.getDifficulty();
    }

    @Override
    public boolean isDifficultyLocked() {
        return parent.isDifficultyLocked();
    }

}
