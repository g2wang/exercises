use std::thread;
use std::sync::mpsc;
use std::sync::Arc;
use std::sync::Mutex;

pub struct ThreadPool {
    workers: Vec<Worker>,
    sender: mpsc::Sender<Job>
}

type Job = Box<dyn FnOnce() + Send + 'static>;

impl ThreadPool {

    /// Creat a new ThreadPool
    /// 
    /// The size is the number of threads in the pool
    /// 
    /// # Panics
    /// The `new` function will panic if the size is 0
    pub fn new(size: usize) -> ThreadPool {
        assert!(size > 0);
        let (sender, receiver) = mpsc::channel();
        let receiver = Arc::new(Mutex::new(receiver));
        let mut workers = Vec::with_capacity(4);
        for id in 0..size {
            workers.push(Worker::new(id, Arc::clone(&receiver)));
        }
        ThreadPool{workers, sender}
    }

    pub fn execute<F>(&self, f: F)
    where
        F: FnOnce() + Send + 'static
    {
        let job = Box::new(f);
        self.sender.send(job).unwrap();
    }
}

struct Worker {
    id: usize,
    thread: thread::JoinHandle<()>
}

impl Worker {
    // correct implementation
    fn new(id: usize, receiver: Arc<Mutex<mpsc::Receiver<Job>>>) -> Worker {
        let thread = thread::spawn(move || loop {
                let job = receiver.lock().unwrap().recv().unwrap();
                println!("Worker {} got job; executing.", id);
                job();
            }
        );
        Worker{id, thread}
    }

    // wrong implementation using while let
    // fn new(id: usize, receiver: Arc<Mutex<mpsc::Receiver<Job>>>) -> Worker {
    //     let thread = thread::spawn(move || 
    //         // wrong implementation
    //         // the lock is held in the whole block, resulting in one thread 
    //         // always holding the lock
    //         while let Ok(job) = receiver.lock().unwrap().recv() {
    //             println!("Worker {} got job; executing.", id);
    //             job();
    //         }
    //     );
    //     Worker{id, thread}
    // }
}