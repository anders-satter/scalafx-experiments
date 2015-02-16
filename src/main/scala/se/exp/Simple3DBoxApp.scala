package se.exp

import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.SceneAntialiasing
import scalafx.scene.Scene
import scalafx.scene.shape.Box
import scalafx.scene.paint.PhongMaterial
import scalafx.scene.paint.Color
import scalafx.scene.shape.DrawMode
import scalafx.scene.PerspectiveCamera
import scalafx.scene.transform.Rotate
import scalafx.scene.transform.Translate
import scala.collection.immutable.Seq
import scalafx.scene.control.TextField
import scalafx.Includes._

/** Demo of a triangular frame of a 3D box, originally based on example in Ensemble 8. */
object Simple3DBoxApp extends JFXApp {

  stage = new PrimaryStage {
    scene = new Scene(300, 300, true,  SceneAntialiasing.Balanced) {
      // 3D content
      content = new Box {
        width = 3
        height = 3
        depth = 3
        material = new PhongMaterial(Color.Red)
        drawMode = DrawMode.Line
      }

      // Background
      fill = Color.AliceBlue

      // Modify point of view
      camera = new PerspectiveCamera(true) {
        transforms
        transforms += (
          new Rotate(-20, Rotate.YAxis),
          new Rotate(-20, Rotate.XAxis),
          new Translate(0, 0, -15))
      }
    }
  }
}