package me.drex.world_gamerules.data;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.Difficulty;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.storage.ServerLevelData;
import net.minecraft.world.level.timers.TimerQueue;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class SavedWorldLevelData extends SavedData implements ServerLevelData {

    private long dayTime;

    private int clearWeatherTime;
    private boolean raining;
    private int rainTime;
    private boolean thundering;
    private int thunderTime;

    @Override
    public CompoundTag save(CompoundTag compoundTag, HolderLookup.Provider provider) {
        compoundTag.putLong("day_time", dayTime);
        compoundTag.putInt("clear_weather_time", clearWeatherTime);
        compoundTag.putBoolean("raining", raining);
        compoundTag.putInt("rain_time", rainTime);
        compoundTag.putBoolean("thundering", thundering);
        compoundTag.putInt("thunder_time", thunderTime);
        return compoundTag;
    }

    public static SavedWorldLevelData load(CompoundTag compoundTag) {
        SavedWorldLevelData data = new SavedWorldLevelData();
        data.dayTime = compoundTag.getLong("day_time");
        data.clearWeatherTime = compoundTag.getInt("clear_weather_time");
        data.raining = compoundTag.getBoolean("raining");
        data.rainTime = compoundTag.getInt("rain_time");
        data.thundering = compoundTag.getBoolean("thundering");
        data.thunderTime = compoundTag.getInt("thunder_time");
        return data;
    }

    @Override
    public String getLevelName() {
        throw new UnsupportedOperationException();
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
        throw new UnsupportedOperationException();
    }

    @Override
    public void setWanderingTraderSpawnDelay(int i) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getWanderingTraderSpawnChance() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setWanderingTraderSpawnChance(int i) {
        throw new UnsupportedOperationException();
    }

    @Override
    public @Nullable UUID getWanderingTraderId() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setWanderingTraderId(UUID uUID) {
        throw new UnsupportedOperationException();
    }

    @Override
    public GameType getGameType() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setWorldBorder(WorldBorder.Settings settings) {
        throw new UnsupportedOperationException();
    }

    @Override
    public WorldBorder.Settings getWorldBorder() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isInitialized() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setInitialized(boolean bl) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isAllowCommands() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setGameType(GameType gameType) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TimerQueue<MinecraftServer> getScheduledEvents() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setGameTime(long l) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setDayTime(long l) {
        this.dayTime = l;
        setDirty();
    }

    @Override
    public GameRules getGameRules() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setSpawn(BlockPos blockPos, float f) {
        throw new UnsupportedOperationException();
    }

    @Override
    public BlockPos getSpawnPos() {
        throw new UnsupportedOperationException();
    }

    @Override
    public float getSpawnAngle() {
        throw new UnsupportedOperationException();
    }

    @Override
    public long getGameTime() {
        throw new UnsupportedOperationException();
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
        throw new UnsupportedOperationException();
    }

    @Override
    public Difficulty getDifficulty() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isDifficultyLocked() {
        throw new UnsupportedOperationException();
    }

}
