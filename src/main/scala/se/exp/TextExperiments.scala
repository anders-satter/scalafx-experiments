package se.exp

import scalafx.application.JFXApp
import scalafx.scene.control.TextField
import scalafx.scene.control._
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Parent
import scalafx.scene.Scene
import scalafx.scene.Group
import scalafx.Includes._
import scalafx.stage.StageStyle
import scalafx.scene.control.Label
import scalafx.scene.layout.StackPane
import scalafx.scene.Node
import scalafx.scene.input.MouseEvent
import scalafx.beans.property.BooleanProperty
import scalafx.scene.layout.FlowPane

object TextExperiments extends JFXApp {
  private val dragModeActiveProperty = new BooleanProperty(this, "dragModeActive", true)

  var textField = new TextField {
    text = "Bingo"
    //style = "-fx-background-color: rgba(0, 100, 100, 0.0); -fx-text-fill: goldenrod; -fx-font-style: italic; -fx-font-weight: normal; -fx-padding: 0 0 20 0;"
    style = "-fx-background-color: rgba(0, 100, 100, 0.0); -fx-text-fill: white; -fx-font-weight: normal; -fx-padding: 0 0 20 0;"
  }

  textField = makeDraggable(textField).asInstanceOf[TextField]
  
  val label = new Label {
    text = "prince"
    style = "-fx-text-fill: white; -fx-font-style: regular; -fx-font-weight: normal; -fx-padding: 0 0 20 0;"
    //style = "-fx-text-fill: goldenrod; -fx-font-style: italic; -fx-font-weight: bold; -fx-padding: 0 0 20 0;"
  }

  val glassPane = new FlowPane {
    maxWidth = 300
    maxHeight = 300  
    style = "-fx-background-color: rgba(0, 100, 100, 0.0); -fx-background-radius: 10;"
    children ++= Seq(label, textField)
  }

  //  def createContent: Parent = {
  //    new Group {
  //      //children ++= Seq(textField, label)
  //      children ++= Seq(label)
  //      style = "-fx-background-color: rgba(0, 100, 100, 0.5); -fx-background-radius: 10;"
  //    }
  //  }

  private def makeDraggable(node: Node): Node = {

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


  /*
   * all the over stuff that we are showing abou
   */
  val layout = new StackPane {
    style = "-fx-background-color: black; -fx-font-size: 10; -fx-padding: 10;"
    children ++= Seq(glassPane)
    minHeight = 300
    minWidth = 300
  }

  stage = new PrimaryStage {
    height = 300
    width = 300

    //initStyle(StageStyle.TRANSPARENT)
    scene = new Scene {
      resizable = true
      content = layout
    }
  }

  /**
   * Drag context 
   */
  private final class DragContext {
    var mouseAnchorX: Double = 0d
    var mouseAnchorY: Double = 0d
    var initialTranslateX: Double = 0d
    var initialTranslateY: Double = 0d
  }

}