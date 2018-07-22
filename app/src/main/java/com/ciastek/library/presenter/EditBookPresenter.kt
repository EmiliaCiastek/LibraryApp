package com.ciastek.library.presenter

import com.ciastek.library.EditBookContract
import com.ciastek.library.model.Book
import com.ciastek.library.model.db.BookDao

class EditBookPresenter(private val book: Book, private val bookDao: BookDao) : EditBookContract.Presenter {
    private var view: EditBookContract.View? = null

    //TODO: add book validator
    override fun onSaveBookClicked(title: String, author: String, isbn: String, isRead: Boolean) {
        val changedBook = Book(book.id, title, author, isbn, isRead)
        bookDao.updateBook(changedBook)
        view?.bookSaved(changedBook)
    }

    override fun onRemoveBookClicked() {
        //TODO: add dialog
        bookDao.deleteBook(book)
        view?.bookDeleted(book)
    }

    override fun attachView(view: EditBookContract.View) {
        this.view = view
        setBook()
    }

    override fun detachView() {
        view = null
    }

    private fun setBook() {
        view?.setAuthor(book.author)
        view?.setTitle(book.title)
        view?.setIsbn(book.isbn)
        view?.isRead(book.isRead)
    }
}