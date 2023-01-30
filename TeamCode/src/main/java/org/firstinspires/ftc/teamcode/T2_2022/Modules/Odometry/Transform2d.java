package org.firstinspires.ftc.teamcode.T2_2022.Modules.Odometry;

public class Transform2d {
  private final Translation2d m_translation;
  private final Rotation2d m_rotation;

  public Transform2d(Translation2d translation, Rotation2d rotation) {
    m_translation = translation;
    m_rotation = rotation;
  }

  public Translation2d getTranslation() {
    return m_translation;
  }

  public Rotation2d getRotation() {
    return m_rotation;
  }
}
