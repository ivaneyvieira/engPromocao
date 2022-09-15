package br.com.astrosoft.framework.view

import org.claspina.confirmdialog.ButtonOption
import org.claspina.confirmdialog.ConfirmDialog

object MessageUtils {
  fun showError(msg: String) {
    ConfirmDialog.createError().withCaption("Erro do aplicativo").withMessage(msg).open()
  }

  fun showWarning(msg: String) {
    ConfirmDialog.createWarning().withCaption("Aviso").withMessage(msg).open()
  }

  fun showInformation(msg: String) {
    ConfirmDialog.createInfo().withCaption("Informação").withMessage(msg).open()
  }

  fun showReport(chave: String, report: ByteArray) {
    SubWindowPDF(chave, report).open()
  }

  fun showQuestion(msg: String, execYes: () -> Unit) {
    showQuestion(msg, execYes) {}
  }

  fun showQuestion(msg: String, execYes: () -> Unit, execNo: () -> Unit) {
    ConfirmDialog
      .createQuestion()
      .withCaption("Confirmação")
      .withMessage(msg)
      .withYesButton({
                       execYes()
                     },
                     ButtonOption.caption("Sim"))
      .withNoButton({ execNo() }, ButtonOption.caption("Não"))
      .open()
  }
}