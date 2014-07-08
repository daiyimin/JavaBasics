package com.mgnt.excel;

public class Rect {
    private Point topLeft;
    private Point bottomRight;
    
    public Rect(Point topLeft, Point bottomRight) {
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
    }
    
    public Rect(int top, int left, int bottom, int right) {
        topLeft = new Point(left, top);
        bottomRight = new Point(right, bottom);
    }
    
    public Point getTopLeft() {
        return topLeft;
    }
    public void setTopLeft(Point topLeft) {
        this.topLeft = topLeft;
    }
    public Point getBottomRight() {
        return bottomRight;
    }
    public void setBottomRight(Point bottomRight) {
        this.bottomRight = bottomRight;
    }
    
    public int getTop() {
        return topLeft.getY();
    }
    public int getLeft() {
        return topLeft.getX();
    }
    public int getBottom() {
        return bottomRight.getY();
    }
    public int getRight() {
        return bottomRight.getX();
    }
    
    /**
     * Calculate height of rectangular
     * @return
     */
    public int getHeight() {
        // Y is zero based, so add additional 1
        return bottomRight.getY() - topLeft.getY() + 1;
    }
    
    /**
     * Calculate width of rectangular
     * @return
     */
    public int getWidth() {
        // X is zero based, so add additional 1
        return bottomRight.getX() - topLeft.getX() + 1;
    }
}
