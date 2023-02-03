package org.firstinspires.ftc.teamcode.Utils;

public class Rotation2d {
  private final double m_value;
  private final double m_cos;
  private final double m_sin;

  public Rotation2d(double x, double y) {
    double magnitude = Math.hypot(x, y);
    if (magnitude > 1e-6) {
      m_sin = y / magnitude;
      m_cos = x / magnitude;
    } else {
      m_sin = 0.0;
      m_cos = 1.0;
    }
    m_value = Math.atan2(m_sin, m_cos);
  }

  public Rotation2d(double value) {
    while (value > Math.PI) value -= 2 * Math.PI;
    while (value < -Math.PI) value += 2 * Math.PI;
    m_value = value;
    m_cos = Math.cos(value);
    m_sin = Math.sin(value);
  }

  public Rotation2d plus(Rotation2d other) {
    return rotateBy(other);
  }

  public Rotation2d rotateBy(Rotation2d other) {
    return new Rotation2d(
        m_cos * other.m_cos - m_sin * other.m_sin, m_cos * other.m_sin + m_sin * other.m_cos);
  }

  public double getRadians() {
    return m_value;
  }

  public double getDegrees() {
    return Math.toDegrees(m_value);
  }

  public double getCos() {
    return m_cos;
  }

  public double getSin() {
    return m_sin;
  }

  @Override
  public String toString() {
    return String.format("Rotation2d(Rads: %.2f, Deg: %.2f)", m_value, Math.toDegrees(m_value));
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Rotation2d) {
      return Math.abs(((Rotation2d) obj).m_value - m_value) < 1E-9;
    }
    return false;
  }
}
