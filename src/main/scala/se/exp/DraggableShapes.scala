package se.exp

import scalafx.application.JFXApp
import scalafx.Includes._
import jfxtras.labs.scene.layout.ScalableContentPane
import scalafx.scene.layout.Pane
import scalafx.scene.shape.Shape
import scalafx.scene.shape.Rectangle
import jfxtras.labs.util.event.MouseControlUtil
import scalafx.application.JFXApp.PrimaryStage
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import scalafx.scene.paint.Color
import se.exp.TextExperiments.DragContext
import scalafx.scene.Node
import scalafx.scene.Group
import scalafx.scene.input.MouseEvent
import scalafx.beans.property.BooleanProperty

object DraggableShapes extends JFXApp {
  private val dragModeActiveProperty = new BooleanProperty(this, "dragModeActive", true)

  val scaledPane = new ScalableContentPane()
  val root: Pane = scaledPane.getContentPane
  root.style = "-fx-background-color : linear-gradient(to bottom, rgb(20,20,20), rgb(30,60,80));"
  var shape: Shape = new Rectangle {
    width = 51
    height = 51
    stroke = Color.White
  }
  
  //MouseControlUtil.makeDraggable(shape)
  //shape = makeDraggable(shape)
  root.children ++= List(shape)

  stage = new PrimaryStage {
    title = "MakeNodesDraggable"
    scene = new Scene(scaledPane, 400, 400)
  }

  private def makeDraggable(node: Node):Unit = {

    val dragContext = new DragContext()

    new Group(node) {
      filterEvent(MouseEvent.Any) {
        (me: MouseEvent) =>
          if (dragModeActiveProperty()) {
            me.eventType match {
              case MouseEvent.MousePressed =>
                dragContext.mouseAnchorX = me.x
                dragContext.mouseAnchorY = me.y
                dragContext.initialTranslateX = node.translateX()
                dragContext.initialTranslateY = node.translateY()
              case MouseEvent.MouseDragged =>
                node.translateX = dragContext.initialTranslateX + me.x - dragContext.mouseAnchorX
                node.translateY = dragContext.initialTranslateY + me.y - dragContext.mouseAnchorY
              case _ =>
            }
            me.consume()
          }
      }
    }
  }

  private final class DragContext {
    var mouseAnchorX: Double = 0d
    var mouseAnchorY: Double = 0d
    var initialTranslateX: Double = 0d
    var initialTranslateY: Double = 0d
  }

}

