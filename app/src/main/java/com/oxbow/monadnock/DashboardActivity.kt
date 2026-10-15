package com.oxbow.monadnock

import android.app.Activity
import android.content.Intent
import android.os.Bundle

import android.util.Log
import android.view.View
import android.widget.AdapterView

import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.loader.app.LoaderManager
import androidx.loader.content.Loader
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.oxbow.monadnock.service.ApiDataCallback
import com.oxbow.monadnock.service.TwinPeaksApi
import com.oxbow.monadnock.service.Utils
import com.oxbow.monadnock.service.api.ReceiptsLoader
import com.oxbow.monadnock.service.domain.Receipt
import com.oxbow.monadnock.service.domain.ReceiptAdapter
import com.oxbow.monadnock.service.domain.TwinPeak

//import com.oxbow.monadnock.databinding.ActivityDashboardBinding

class DashboardActivity : AppCompatActivity(), LoaderManager.LoaderCallbacks<List<Receipt>>,
    AdapterView.OnItemClickListener, ApiDataCallback {

    private lateinit var recyclerview: RecyclerView
    private lateinit var mAdapter: ReceiptAdapter

    private lateinit var mTwinPeaksApi: TwinPeaksApi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //val binding: ActivityDashboardBinding = DataBindingUtil.setContentView(this, R.layout.activity_dashboard)
        //setContentView(R.layout.activity_dashboard)

        Log.d(TAG, "onCreate: started")

        mTwinPeaksApi = TwinPeaksApi.getInstance(this)

        recyclerview = findViewById(R.id.receipt_recycler_view)
        recyclerview.setLayoutManager(LinearLayoutManager(this))

        mAdapter = ReceiptAdapter(this)
        recyclerview.setAdapter(mAdapter)
        // mList.setOnItemClickListener(this);

        if (!TwinPeaksApi.getInstance(this).hasToken()) {
            //Launch account picker for first runs
            val intent = Intent(this, LoginActivity::class.java)
            startActivityForResult(intent, REQUEST_AUTH)
        } else {
            getSupportLoaderManager().initLoader(0, null, this)
        }
    }

    override fun onResume() {
        super.onResume()
        refreshLoader()
        mTwinPeaksApi.registerDataCallback(this)
    }

    override fun onPause() {
        super.onPause()
        mTwinPeaksApi.unregisterDataCallback(this)
    }

    override fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long) {
        /*final Receipt item = mAdapter.getItem(position);
        new AlertDialog.Builder(this)
                .setTitle("Copy Data?")
                .setMessage("Do you want to copy this attachments data to the clipboard?")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Copy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ClipboardManager manager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                        manager.setPrimaryClip(ClipData.newPlainText("Receipt Item", item.getData()));

                        Utils.showToast(DashboardActivity.this, "Saved to Clipboard");
                    }
                })
                .show();*/
    }

    /* LoaderCallbacks Methods */
    override fun onCreateLoader(id: Int, args: Bundle?): Loader<List<Receipt>> {
        return ReceiptsLoader(this)
    }

    override fun onLoadFinished(loader: Loader<List<Receipt>>, data: List<Receipt>?) {
        //if (recyclerview..getEmptyView() == null) {
        //mList.setEmptyView(findViewById(R.id.empty));
        //}

        if (data == null) {
            Utils.showToast(this, "Unable to load receipts")
            return
        }
        Log.d(TAG, "Finished loading receipts")
        //mAdapter.setNotifyOnChange(false);
        //mAdapter.clear();
        mAdapter.updateList(data)
    }

    override fun onLoaderReset(loader: Loader<List<Receipt>>) {

        //mAdapter.clear();
    }

    private fun refreshLoader() {
        getSupportLoaderManager().restartLoader(0, getIntent().getExtras(), this)
    }

    /* ApiDataCallback Methods */

    override fun onTwinPeakResponse(twinPeak: TwinPeak) {
        refreshLoader()
    }

    override fun onAttachmentResponse() {
        refreshLoader()
    }

    companion object {

        private val TAG = "DashboardActivity"

        private val REQUEST_AUTH = 42
    }
}
