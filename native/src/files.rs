use jni::objects::{JObject, JValue};
use jni::JNIEnv;

pub fn has_genymotion_files(env: &mut JNIEnv) -> bool {
    let result = check_files_present(env, &GENYMOTION_FILES);
    result
}

pub fn has_pipes(env: &mut JNIEnv) -> bool {
    let result = check_files_present(env, &PIPES);
    result
}

pub fn has_emu_files(env: &mut JNIEnv) -> bool {
    let result = check_files_present(env, &EMU_FILES);
    result
}

pub fn has_x86_files(env: &mut JNIEnv) -> bool {
    let result = check_files_present(env, &X86_FILES);
    result
}

pub fn has_andy_files(env: &mut JNIEnv) -> bool {
    let result = check_files_present(env, &ANDY_FILES);
    result
}

pub fn has_nox_files(env: &mut JNIEnv) -> bool {
    let result = check_files_present(env, &NOX_FILES);
    result
}

pub fn has_bluestack_files(env: &mut JNIEnv) -> bool {
    let result = check_files_present(env, &BLUE_STACKS_FILES);
    result
}

fn check_files_present(env: &mut JNIEnv, suspicious_file_paths: &[&str]) -> bool {
    let mut result = false;

    let file_clz = env.find_class("java/io/File").unwrap();

    for file_path in suspicious_file_paths.iter() {
        let file = env
            .new_object(
                &file_clz,
                "(Ljava/lang/String;)V",
                &[JValue::Object(&JObject::from(
                    env.new_string(file_path).unwrap(),
                ))],
            )
            .unwrap();

        result = env
            .call_method(file, "exists", "()Z", &[])
            .unwrap()
            .z()
            .unwrap();

        if result {
            return result;
        }
    }

    return result;
}

///  Files which indicates that it is a Genymotion emulator.
const GENYMOTION_FILES: [&str; 2] = ["/dev/socket/genyd", "/dev/socket/baseband_genyd"];

/// Pipes which indicates that it is most likely an emulator.
const PIPES: [&str; 3] = ["/dev/socket/qemud", "/dev/qemu_pipe", "/dev/goldfish_pipe"];

/// Files which indicates that it is most likely an emulator.
const EMU_FILES: [&str; 3] = [
    "/system/lib/libc_malloc_debug_qemu.so",
    "/sys/qemu_trace",
    "/system/bin/qemu-props",
];

/// Pipes which indicates that it is most likely an emulator.
const X86_FILES: [&str; 9] = [
    "ueventd.android_x86.rc",
    "x86.prop",
    "ueventd.ttVM_x86.rc",
    "init.ttVM_x86.rc",
    "fstab.ttVM_x86",
    "fstab.vbox86",
    "init.vbox86.rc",
    "ueventd.vbox86.rc",
    "ueventd.ranchu.rc",
];

/// Pipes which indicates that it is an Andy emulator.
const ANDY_FILES: [&str; 2] = ["fstab.andy", "ueventd.andy.rc"];

/// Pipes which indicates that it is a Nox emulator.
const NOX_FILES: [&str; 5] = [
    "fstab.nox",
    "init.nox.rc",
    "ueventd.nox.rc",
    "/BigNoxGameHD",
    "/YSLauncher",
];

/// Pipes which indicates that it is a BlueStacks emulator.
const BLUE_STACKS_FILES: [&str; 2] = [
    "/Android/data/com.bluestacks.home",
    "/Android/data/com.bluestacks.settings",
];
