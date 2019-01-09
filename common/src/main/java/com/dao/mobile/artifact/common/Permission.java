package com.dao.mobile.artifact.common;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

/**
 * Gerencia permissão em tempo de execução utilizado a partir da api 23.
 *
 * @author Diogo Oliveira.
 */
public class Permission
{
    public static final int REQUEST_CONTACTS = 1;
    public static final int REQUEST_CAMERA = 2;
    public static final int REQUEST_LOCATION = 3;
    public static final int REQUEST_STORAGE = 4;
    public static final int REQUEST_MULTIPLE_PERMISSIONS = 5;

    public static final String CAMERA = Manifest.permission.CAMERA;
    public static final String CONTACTS = Manifest.permission.GET_ACCOUNTS;
    public static final String LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    public static final String STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE;

    private static final String PREFERENCE_PERMISSION = "PREFERENCE_PERMISSION";
    private static final String PERMISSION_CONTACTS_SHOW = "PERMISSION_CONTACTS_SHOW";
    private static final String PERMISSION_CAMERA_SHOW = "PERMISSION_CAMERA_SHOW";
    private static final String PERMISSION_STORAGE_SHOW = "PERMISSION_STORAGE_SHOW";
    private static final String PERMISSION_LOCATION_SHOW = "PERMISSION_LOCATION_SHOW";

    private static final String[] PERMISSIONS_CONTACTS = {CONTACTS};
    private static final String[] PERMISSIONS_CAMERA = {CAMERA};
    private static final String[] PERMISSIONS_STORAGE = {STORAGE};
    private static final String[] PERMISSIONS_LOCATION = {LOCATION};

    private final SharedPreferences preferences;
    private final View anchor;

    private AppCompatActivity activity;
    private Fragment fragment;
    private boolean repeat;

    /**
     * Gerencia permissões que são concedidas em tempo de execução.
     *
     * @param activity "activity atual".
     * @param anchor   "view pai do layout".
     */
    public Permission(@NonNull AppCompatActivity activity, @NonNull View anchor)
    {
        this.activity = activity;
        this.anchor = anchor;
        this.preferences = getContext().getSharedPreferences(PREFERENCE_PERMISSION, Context.MODE_PRIVATE);
    }

    /**
     * Gerencia permissões que são concedidas em tempo de execução.
     *
     * @param fragment "fragment atual".
     * @param anchor   "view pai do layout".
     */
    public Permission(@NonNull Fragment fragment, @NonNull View anchor)
    {
        this.fragment = fragment;
        this.anchor = anchor;
        this.preferences = getContext().getSharedPreferences(PREFERENCE_PERMISSION, Context.MODE_PRIVATE);
    }

    public Fragment getFragment()
    {
        return fragment;
    }

    public AppCompatActivity getActivity()
    {
        return activity;
    }

    /**
     * Exibe uma snackBar informando que a permissão foi concedida.
     *
     * @param requestCode  da permissão.
     * @param grantResults grantResults.
     *
     * @return true se concedida.
     */
    public boolean accepted(int requestCode, int[] grantResults)
    {
        if(grantResults.length < 1)
        {
            return false;
        }
        else
        {
            if(requestCode == REQUEST_MULTIPLE_PERMISSIONS)
            {
                for(int result : grantResults)
                {
                    if(result != PackageManager.PERMISSION_GRANTED)
                    {
                        return false;
                    }
                }
            }
            else
            {
                for(int result : grantResults)
                {
                    repeat = (result != PackageManager.PERMISSION_GRANTED);

                    switch(requestCode)
                    {
                        case REQUEST_CONTACTS:
                        {
                            if(repeat)
                            {
                                contacts();
                                return false;
                            }
                            else
                            {
                                preferences.edit().remove(PERMISSION_CONTACTS_SHOW).apply();
                                break;
                            }
                        }
                        case REQUEST_CAMERA:
                        {
                            if(repeat)
                            {
                                camera();
                                return false;
                            }
                            else
                            {
                                preferences.edit().remove(PERMISSION_CAMERA_SHOW).apply();
                                break;
                            }
                        }
                        case REQUEST_STORAGE:
                        {
                            if(repeat)
                            {
                                storage();
                                return false;
                            }
                            else
                            {
                                preferences.edit().remove(PERMISSION_STORAGE_SHOW).apply();
                                break;
                            }
                        }
                        case REQUEST_LOCATION:
                        {
                            if(repeat)
                            {
                                location();
                                return false;
                            }
                            else
                            {
                                preferences.edit().remove(PERMISSION_LOCATION_SHOW).apply();
                                break;
                            }
                        }
                        default:
                        {
                            return false;
                        }
                    }
                }
            }

            Snackbar.make(anchor, R.string.permission_cmon_accepted, Snackbar.LENGTH_SHORT).show();
            return true;
        }
    }

    public Context getContext()
    {
        return (isActivity() ? activity : fragment.getActivity());
    }

    /**
     * Verifica se tem permissão para acesso a conta.
     *
     * @return (true se permissão concedida)
     */
    public boolean isPermissionContacts()
    {
        return (ActivityCompat.checkSelfPermission(getContext(), CONTACTS) == PackageManager.PERMISSION_GRANTED);
    }

    /**
     * Verifica se tem permissão para utilizar a camera.
     *
     * @return (true se permissão concedida)
     */
    public boolean isPermissionCamera()
    {
        return (ActivityCompat.checkSelfPermission(getContext(), CAMERA) == PackageManager.PERMISSION_GRANTED);
    }

    /**
     * Verifica se tem permissão para escrever no disco.
     *
     * @return (true se permissão concedida)
     */
    public boolean isPermissionStorage()
    {
        return (ActivityCompat.checkSelfPermission(getContext(), STORAGE) == PackageManager.PERMISSION_GRANTED);
    }

    /**
     * Verifica se tem permissão para utilizar a localização.
     *
     * @return (true se permissão concedida)
     */
    public boolean isPermissionLocation()
    {
        return (ActivityCompat.checkSelfPermission(getContext(), LOCATION) == PackageManager.PERMISSION_GRANTED);
    }

    /**
     * Solicita a permissão para o usuário para utilizar a conta.
     */
    public void contacts()
    {
        if(isActivity())
        {
            contactsActivity();
        }
        else
        {
            contactsFragment();
        }
    }

    private void contactsActivity()
    {
        if(ActivityCompat.shouldShowRequestPermissionRationale(activity, CONTACTS))
        {
            if(repeat)
            {
                showDialogInformative(R.string.permission_cmon_dialog_contacts, REQUEST_CONTACTS);
                reportPreferences(PERMISSION_CAMERA_SHOW);
                repeat = false;
            }
            else
            {
                ActivityCompat.requestPermissions(activity, PERMISSIONS_CONTACTS, REQUEST_CONTACTS);
            }
        }
        else
        {
            if(preferences.getBoolean(PERMISSION_CONTACTS_SHOW, false))
            {
                showSnackbarknowMore(R.string.permission_cmon_rationale_account, R.string.permission_cmon_dialog_contacts_accept);
            }
            else
            {
                ActivityCompat.requestPermissions(activity, PERMISSIONS_CONTACTS, REQUEST_CONTACTS);
            }
        }
    }

    private void contactsFragment()
    {
        if(fragment.shouldShowRequestPermissionRationale(CONTACTS))
        {
            if(repeat)
            {
                showDialogInformative(R.string.permission_cmon_dialog_contacts, REQUEST_CONTACTS);
                reportPreferences(PERMISSION_CAMERA_SHOW);
                repeat = false;
            }
            else
            {
                fragment.requestPermissions(PERMISSIONS_CONTACTS, REQUEST_CONTACTS);
            }
        }
        else
        {
            if(preferences.getBoolean(PERMISSION_CONTACTS_SHOW, false))
            {
                showSnackbarknowMore(R.string.permission_cmon_rationale_account, R.string.permission_cmon_dialog_contacts_accept);
            }
            else
            {
                fragment.requestPermissions(PERMISSIONS_CONTACTS, REQUEST_CONTACTS);
            }
        }
    }

    /**
     * Solicita a permissão para o usuário para utilizar a camera.
     */
    public void camera()
    {
        if(isActivity())
        {
            cameraActivity();
        }
        else
        {
            cameraFragment();
        }
    }

    private void cameraActivity()
    {
        if(ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.CAMERA))
        {
            if(repeat)
            {
                showDialogInformative(R.string.permission_cmon_dialog_camera, REQUEST_CAMERA);
                reportPreferences(PERMISSION_CAMERA_SHOW);
                repeat = false;
            }
            else
            {
                ActivityCompat.requestPermissions(activity, PERMISSIONS_CAMERA, REQUEST_CAMERA);
            }
        }
        else
        {
            if(preferences.contains(PERMISSION_CAMERA_SHOW) && preferences.getBoolean(PERMISSION_CAMERA_SHOW, false))
            {
                showSnackbarknowMore(R.string.permission_cmon_rationale_camera, R.string.permission_cmon_dialog_camera_accept);
            }
            else
            {
                ActivityCompat.requestPermissions(activity, PERMISSIONS_CAMERA, REQUEST_CAMERA);
            }
        }
    }

    private void cameraFragment()
    {
        if(fragment.shouldShowRequestPermissionRationale(Manifest.permission.CAMERA))
        {
            if(repeat)
            {
                showDialogInformative(R.string.permission_cmon_dialog_camera, REQUEST_CAMERA);
                reportPreferences(PERMISSION_CAMERA_SHOW);
                repeat = false;
            }
            else
            {
                fragment.requestPermissions(PERMISSIONS_CAMERA, REQUEST_CAMERA);
            }
        }
        else
        {
            if(preferences.getBoolean(PERMISSION_CAMERA_SHOW, false))
            {
                showSnackbarknowMore(R.string.permission_cmon_rationale_camera, R.string.permission_cmon_dialog_camera_accept);
            }
            else
            {
                fragment.requestPermissions(PERMISSIONS_CAMERA, REQUEST_CAMERA);
            }
        }
    }

    /**
     * Solicita a permissão para o usuário para escrever no disco.
     */
    public void storage()
    {
        if(isActivity())
        {
            storageActivity();
        }
        else
        {
            storageFragment();
        }
    }

    private void storageActivity()
    {
        if(ActivityCompat.shouldShowRequestPermissionRationale(activity, STORAGE))
        {
            if(repeat)
            {
                showDialogInformative(R.string.permission_cmon_dialog_storage, REQUEST_STORAGE);
                reportPreferences(PERMISSION_CAMERA_SHOW);
                repeat = false;
            }
            else
            {
                ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, REQUEST_STORAGE);
            }
        }
        else
        {
            if(preferences.getBoolean(PERMISSION_STORAGE_SHOW, false))
            {
                showSnackbarknowMore(R.string.permission_cmon_rationale_storage, R.string.permission_cmon_dialog_storage_accept);
            }
            else
            {
                ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, REQUEST_STORAGE);
            }
        }
    }

    private void storageFragment()
    {
        if(fragment.shouldShowRequestPermissionRationale(STORAGE))
        {
            if(repeat)
            {
                showDialogInformative(R.string.permission_cmon_dialog_storage, REQUEST_STORAGE);
                reportPreferences(PERMISSION_CAMERA_SHOW);
                repeat = false;
            }
            else
            {
                fragment.requestPermissions(PERMISSIONS_STORAGE, REQUEST_STORAGE);
            }
        }
        else
        {
            if(preferences.getBoolean(PERMISSION_STORAGE_SHOW, false))
            {
                showSnackbarknowMore(R.string.permission_cmon_rationale_storage, R.string.permission_cmon_dialog_storage_accept);
            }
            else
            {
                fragment.requestPermissions(PERMISSIONS_STORAGE, REQUEST_STORAGE);
            }
        }
    }

    /**
     * Solicita a permissão para o usuário para utilizar a localização.
     */
    public void location()
    {
        if(isActivity())
        {
            locationActivity();
        }
        else
        {
            locationFragment();
        }
    }

    private void locationActivity()
    {
        if(ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.ACCESS_FINE_LOCATION))
        {
            if(repeat)
            {
                showDialogInformative(R.string.permission_cmon_dialog_location, REQUEST_LOCATION);
                reportPreferences(PERMISSION_CAMERA_SHOW);
                repeat = false;
            }
            else
            {
                ActivityCompat.requestPermissions(activity, PERMISSIONS_LOCATION, REQUEST_LOCATION);
            }
        }
        else
        {
            if(preferences.getBoolean(PERMISSION_LOCATION_SHOW, false))
            {
                showSnackbarknowMore(R.string.permission_cmon_rationale_location, R.string.permission_cmon_dialog_location_accept);
            }
            else
            {
                ActivityCompat.requestPermissions(activity, PERMISSIONS_LOCATION, REQUEST_LOCATION);
            }
        }
    }

    private void locationFragment()
    {
        if(fragment.shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION))
        {
            if(repeat)
            {
                showDialogInformative(R.string.permission_cmon_dialog_location, REQUEST_LOCATION);
                reportPreferences(PERMISSION_CAMERA_SHOW);
                repeat = false;
            }
            else
            {
                fragment.requestPermissions(PERMISSIONS_LOCATION, REQUEST_LOCATION);
            }
        }
        else
        {
            if(preferences.getBoolean(PERMISSION_LOCATION_SHOW, false))
            {
                showSnackbarknowMore(R.string.permission_cmon_rationale_location, R.string.permission_cmon_dialog_location_accept);
            }
            else
            {
                fragment.requestPermissions(PERMISSIONS_LOCATION, REQUEST_LOCATION);
            }
        }
    }

    public void multiplePermissions(String... permissions)
    {
        if(isActivity())
        {
            multiplePermissionsActivity(permissions);
        }
        else
        {
            multiplePermissionsFragment(permissions);
        }
    }

    private void multiplePermissionsActivity(String... permissions)
    {
        if(permissions != null)
        {
            List<String> listPermissionsNeeded = new ArrayList<>(permissions.length);

            for(String permission : permissions)
            {
                if(ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED)
                {
                    listPermissionsNeeded.add(permission);
                }
            }

            if(!listPermissionsNeeded.isEmpty())
            {
                ActivityCompat.requestPermissions(activity, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_MULTIPLE_PERMISSIONS);
            }
        }
    }

    private void multiplePermissionsFragment(String... permissions)
    {
        if(permissions != null)
        {
            List<String> listPermissionsNeeded = new ArrayList<>(permissions.length);

            for(String permission : permissions)
            {
                if(ContextCompat.checkSelfPermission(fragment.getContext(), permission) != PackageManager.PERMISSION_GRANTED)
                {
                    listPermissionsNeeded.add(permission);
                }
            }

            if(!listPermissionsNeeded.isEmpty())
            {
                fragment.requestPermissions(listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_MULTIPLE_PERMISSIONS);
            }
        }
    }

    public boolean isActivity()
    {
        return (activity != null);
    }

    private AlertDialog.Builder buildDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setIcon(Drawables.draw(R.drawable.vd_cmon_warning_24dp, R.color.alert_yellow));
        builder.setTitle(R.string.permission_cmon_dialog_title);
        builder.setPositiveButton(android.R.string.ok, null);
        builder.setCancelable(false);
        return builder;
    }

    private void reportPreferences(String key)
    {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, true);
        editor.apply();
    }

    private void showDialogExplicative(int message)
    {
        AlertDialog.Builder builder = buildDialog();
        builder.setMessage(message);
        builder.setNegativeButton(R.string.permission_cmon_dialog_button_settings,
                (dialog, which) -> ApplicationController.getInstance().startDetailsSettings()).show();
    }

    private void showSnackbarknowMore(int message, final int because)
    {
        Snackbar.make(anchor, message, Snackbar.LENGTH_LONG).setAction(R.string.permission_cmon_snackbar_know_more, view -> showDialogExplicative(because)).show();
    }

    private void showDialogInformative(int message, final int request)
    {
        AlertDialog.Builder builder = buildDialog();
        builder.setMessage(message);

        builder.setNegativeButton(R.string.permission_cmon_dialog_button_back, (dialog, which) -> {
            switch(request)
            {
                case REQUEST_CONTACTS:
                {
                    contacts();
                    break;
                }
                case REQUEST_CAMERA:
                {
                    camera();
                    break;
                }
                case REQUEST_STORAGE:
                {
                    storage();
                    break;
                }
                case REQUEST_LOCATION:
                {
                    location();
                    break;
                }
            }
        });

        builder.show();
    }
}
