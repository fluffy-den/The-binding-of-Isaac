package gameWorld;

import resources.DisplaySettings;

public class GameCounter {
    private long lasttp;
    private double tpstep;

    /// Constructors
    /**
     * 
     * @param nexttp
     */
    public GameCounter(double step) {
        assert (step != 0);
        this.lasttp = Game.getImageNum();
        this.tpstep = step;
    }

    /// Methodes
    /**
     * 
     * @return
     */
    public boolean isFinished() {
        long current = Game.getImageNum();
        if ((current - this.lasttp) * this.tpstep >= 1) {
            this.lasttp = current;
            return true;
        }

        return false;
    }

    /**
     * 
     * @return
     */
    public double diffToLastTP() {
        return (double) (Game.getImageNum() - lasttp) / (double) DisplaySettings.FRAME_PER_SECOND;
    }

    /**
     * 
     * @param step
     */
    public void setStep(double step) {
        this.tpstep = step;
    }

    /**
     * 
     * @return
     */
    public double getStep() {
        return this.tpstep;
    }

    /**
     * 
     * @param step
     * @return
     */
    public double addStep(double step) {
        this.tpstep += step;
        return this.tpstep;
    }
}
