package io.freshworks.eao.screens.projectlist


import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.LinearLayout
import io.freshworks.eao.Constants
import io.freshworks.eao.R
import io.freshworks.eao.common.adapters.ProjectAdapter
import io.freshworks.eao.data.model.Project
import io.freshworks.eao.di.InjectionUtils
import kotlinx.android.synthetic.main.activity_project_list.*
import kotlinx.android.synthetic.main.toolbar_with_back.view.*
import kotlinx.android.synthetic.main.toolbar_with_back_and_save.*


class ProjectListActivity : AppCompatActivity(), ProjectListContract.View {

    override lateinit var presenter: ProjectListContract.Presenter
    private lateinit var adapter: ProjectAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project_list)

        toolbar.title.setText(R.string.project_list)
        val projectRepo = InjectionUtils.getProjectRepo()

        ProjectListPresenter(this, projectRepo)
        presenter.initialize()
    }

    override fun setUpBackBtnListener() {
        backBtn.setOnClickListener {
            presenter.onBackBtnClicked()
        }
    }

    override fun setUpSearchViewListener() {
        searchEt.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(query: CharSequence?, p1: Int, p2: Int, p3: Int) {
                presenter.onSearchViewSubmitted(query.toString())
            }
        })
    }

    override fun setUpAddBtnListener() {
        addProjectBtn.setOnClickListener {
            presenter.onAddBtnClicked()
        }
    }

    override fun setUpProjectList() {
        adapter = ProjectAdapter(object : ProjectListListener {
            override fun onProjectClicked(project: Project) {
                val returnIntent = Intent()
                returnIntent.putExtra(Constants.EXTRA_PROJECT_NAME, project.name)
                setResult(Activity.RESULT_OK, returnIntent)
                finish()
            }
        })
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    override fun clearProjectList() {
        recyclerView.visibility = View.GONE
        progressBar.visibility = View.VISIBLE
        adapter.clearProjects()
    }

    override fun addProjectToList(project: Project) {
        adapter.addProject(project)
    }

    override fun addProjectsToList(projects: List<Project>) {
        adapter.addProjects(projects)
        recyclerView.visibility = View.VISIBLE
        progressBar.visibility = View.GONE
    }

    override fun showNewProjectView() {
        val input = EditText(this)
        val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT)

        params.setMargins(20, 0, 20, 0)
        input.hint = getString(R.string.add_project_name_here)

        val filterArray = arrayOfNulls<InputFilter>(1)
        filterArray[0] = InputFilter.LengthFilter(5)
        input.filters = filterArray
        input.layoutParams = params

        val builder = AlertDialog.Builder(this)
        builder.setView(input)
        builder.setMessage(R.string.project_name)
                .setPositiveButton(R.string.okay) { _, _ ->
                    if (input.text.toString() != "") {
                        presenter.onNewProjectSubmitted(input.text.toString())
                    }
                }
                .setNegativeButton(R.string.cancel, null)
                .create()
                .show()
    }

    override fun goToPreviousScreen() {
        finish()
    }
}